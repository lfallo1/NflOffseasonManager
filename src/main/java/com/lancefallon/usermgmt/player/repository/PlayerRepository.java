package com.lancefallon.usermgmt.player.repository;

import com.lancefallon.usermgmt.config.events.ParserProgressEvent;
import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.*;
import com.lancefallon.usermgmt.player.sql.PlayerSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class PlayerRepository extends JdbcDaoSupport implements PlayerSql {

    public PlayerRepository(@Autowired DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * find all players.
     * if an auth object is passed in, then return with a notes object attached for each player
     *
     * @param auth
     * @return
     * @throws DatabaseException
     */
    public List<Player> findAll(OAuth2Authentication auth) throws DatabaseException {
        try {
            return auth != null ? getJdbcTemplate().query(PLAYER_FIND_ALL_WITH_NOTES, new Object[]{auth.getName()}, PLAYER_AND_NOTES_ROW_MAPPER)
                    : getJdbcTemplate().query(PLAYER_FIND_ALL, PLAYER_ROW_MAPPER);
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * find player by id
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    public Player findPlayerById(Integer id) throws DatabaseException {
        try {
            String sql = PLAYER_FIND_ALL + " AND p.id = ?";
            return getJdbcTemplate().queryForObject(sql, new Object[]{id}, PLAYER_ROW_MAPPER);
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get all positions
     *
     * @return
     * @throws DatabaseException
     */
    public List<Position> allPositions() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_POSITIONS, POSITION_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get all position categories
     *
     * @return
     * @throws DatabaseException
     */
    public List<PositionCategory> allPositionCategories() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_POSITION_CATEGORIES, POSITION_CATEGORIES_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get all sides of ball (offense, defense, special teams)
     *
     * @return
     * @throws DatabaseException
     */
    public List<PositionSideOfBall> allPositionSidesOfBall() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_POSITION_SIDES_OF_BALL, POSITION_SIDEOFBALL_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get all colleges
     *
     * @return
     * @throws DatabaseException
     */
    public List<College> allColleges() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_COLLEGES, COLLEGE_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get all conferences
     *
     * @return
     * @throws DatabaseException
     */
    public List<Conference> allConferences() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_CONFERENCES, CONFERENCE_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * get a distinct list of years
     *
     * @return
     * @throws DatabaseException
     */
    public List<Integer> allYears() throws DatabaseException {
        try {
            return getJdbcTemplate().query(QUERY_YEARS, YEARS_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * update notes for a player
     *
     * @param username
     * @param notes
     * @throws DatabaseException
     */
    public void updateNotes(String username, PlayerNote notes) throws DatabaseException {
        try {
            getJdbcTemplate().update("update player_notes set summary=?, strengths=?, weaknesses=?,likeness=?,projected_round=?,overall_grade = ? where username = ? and id = ?",
                    new Object[]{notes.getSummary(), notes.getStrengths(), notes.getWeaknesses(),
                            notes.getLikeness(), notes.getProjectedRound(),
                            notes.getOverallGrade(), username, notes.getId()});
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * add notes for a player
     *
     * @param username
     * @param notes
     * @return
     * @throws DatabaseException
     */
    public Integer addNotes(String username, PlayerNote notes) throws DatabaseException {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
        // set the table name, primary key column name, and the array of column
        // names
        jdbcInsert.setTableName("public.player_notes");
        jdbcInsert.setGeneratedKeyName("id");
        jdbcInsert.setColumnNames(Arrays.asList("username", "player", "summary", "strengths", "weaknesses", "likeness", "projected_round", "overall_grade"));

        // set the values to be inserted
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("username", username);
        parameters.put("player", notes.getPlayer().getId());
        parameters.put("summary", notes.getSummary());
        parameters.put("strengths", notes.getStrengths());
        parameters.put("weaknesses", notes.getWeaknesses());
        parameters.put("likeness", notes.getLikeness());
        parameters.put("projected_round", notes.getProjectedRound());
        parameters.put("overall_grade", notes.getOverallGrade());

        // execute insert
        Number key = null;
        try {
            // perform the insert and return the key
            key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                    parameters));
        } catch (Exception e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }

        // return the key (primary key)
        return ((Number) key).intValue();
    }

    /**
     * add player note tag
     *
     * @param note
     * @param tag
     * @return
     * @throws DatabaseException
     */
    public Integer addPlayerNoteTag(PlayerNote note, NoteTag tag) throws DatabaseException {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
        // set the table name, primary key column name, and the array of column
        // names
        jdbcInsert.setTableName("public.player_notes_tag");
        jdbcInsert.setGeneratedKeyName("id");
        jdbcInsert.setColumnNames(Arrays.asList("note", "tag"));

        // set the values to be inserted
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("note", note.getId());
        parameters.put("tag", tag.getId());

        // execute insert
        Number key = null;
        try {
            // perform the insert and return the key
            key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                    parameters));
        } catch (Exception e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }

        // return the key (primary key)
        return ((Number) key).intValue();
    }

    /**
     * get all player notes for the user
     *
     * @param username
     * @return
     * @throws DatabaseException
     */
    public List<PlayerNote> getByUsername(String username) throws DatabaseException {
        try {
            return getJdbcTemplate().query("select pn.*, array_agg((pnt.tag,t.name)) as tags from player_notes pn left outer join player_notes_tag pnt on pn.id = pnt.note left outer join tag t on pnt.tag = t.id where username = ?", new Object[]{username}, new RowMapper<PlayerNote>() {

                @Override
                public PlayerNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Player player = new Player();
                    player.setId(rs.getInt("player"));

                    //map player tags
                    List<NoteTag> tags = new ArrayList<>();
                    Array tagsArray = rs.getArray("tags");
                    if (tagsArray.getArray() != null) {
                        for (Object tagString : (Object[]) tagsArray.getArray()) {

                            String[] parts = tagString.toString().substring(1, tagString.toString().length() - 1).split(",");
                            if (parts.length == 2 && !StringUtils.isEmpty(parts[0]) && !StringUtils.isEmpty(parts[1])) {
                                tags.add(new NoteTag(Integer.parseInt(parts[0]), parts[1]));
                            }
                        }
                    }

                    return new PlayerNote(rs.getInt("id"), rs.getString("username"), player, rs.getString("summary"),
                            rs.getString("strengths"), rs.getString("weaknesses"), rs.getInt("likeness"),
                            rs.getInt("projected_round"), rs.getInt("overall_grade"), tags);
                }

            });
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * delete a user's notes for a player
     *
     * @param username
     * @param notes
     * @throws DatabaseException
     */
    public void deleteNotes(String username, PlayerNote notes) throws DatabaseException {
        try {

            //delete any note tags
            this.deleteNoteTags(username, notes);

            //delete the note itself
            getJdbcTemplate().update("delete from player_notes where id = ? and username = ?", notes.getId(), username);
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * delete tags by note id
     *
     * @param username
     * @param notes
     */
    public void deleteNoteTags(String username, PlayerNote notes) throws DatabaseException {
        try {

            getJdbcTemplate().update("delete from player_notes_tag where note = ?", notes.getId());
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }


    /**
     * get all tags
     *
     * @return
     * @throws DatabaseException
     */
    public List<NoteTag> getAllTags() throws DatabaseException {
        try {
            return getJdbcTemplate().query("select * from tag", NOTE_TAG_ROW_MAPPER);

        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    /**
     * add player
     *
     * @param player
     * @return
     * @throws DatabaseException
     */
    public Integer addPlayer(Player player) throws DatabaseException {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
        // set the table name, primary key column name, and the array of column
        // names
        jdbcInsert.setTableName("public.player");
        jdbcInsert.setGeneratedKeyName("id");
        jdbcInsert.setColumnNames(Arrays.asList("name", "college", "college_text", "position", "height", "weight", "year", "position_rank", "import_uuid", "source"));

        // set the values to be inserted
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("name", player.getName());
        parameters.put("college", player.getCollege().getId());
        parameters.put("college_text", player.getCollegeText());
        parameters.put("position", player.getPosition().getId());
        parameters.put("height", player.getHeight());
        parameters.put("weight", player.getWeight());
        parameters.put("year", player.getYear());
        parameters.put("position_rank", player.getPositionRank());
        parameters.put("import_uuid", UUID.randomUUID());
        parameters.put("source", "ADMIN");

        // execute insert
        Number key = null;
        try {
            // perform the insert and return the key
            key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                    parameters));
        } catch (Exception e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }

        // return the key (primary key)
        return key.intValue();
    }

    public Integer updatePlayer(Player player) throws DatabaseException {
        try {
            return getJdbcTemplate().update("update public.player set name = ?, college = ?, college_text = ?, position = ?, height = ?, weight = ?, year = ?, position_rank = ? where id = ?",
                    new Object[]{player.getName(), player.getCollege().getId(), player.getCollegeText(), player.getPosition().getId(),
                            player.getHeight(), player.getWeight(), player.getYear(), player.getPositionRank(), player.getId()});
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    // --- Import Events --

    public void addParserProgress(ParserProgressEvent event) throws DatabaseException {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
        // set the table name, primary key column name, and the array of column
        // names
        jdbcInsert.setTableName("public.parser_progress");
        jdbcInsert.setColumnNames(Arrays.asList("id", "username", "date", "started", "finished", "description", "progress"));

        // set the values to be inserted
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("id", event.getId());
        parameters.put("username", event.getUsername());
        parameters.put("date", new Date());
        parameters.put("started", event.getStarted());
        parameters.put("finished", event.getFinished());
        parameters.put("description", event.getDescription());
        parameters.put("progress", event.getProgress());

        // execute insert
        Number key = null;
        try {
            // perform the insert and return the key
            jdbcInsert.execute(new MapSqlParameterSource(
                    parameters));
        } catch (DuplicateKeyException e) {
            this.updateParserProgress(event);
        } catch (Exception e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    public Integer updateParserProgress(ParserProgressEvent event) throws DatabaseException {
        try {
            return getJdbcTemplate().update("update public.parser_progress set date = ?, description = ?, finished = ?, progress = ? where id = ?",
                    new Object[]{event.getDate(), event.getDescription(), event.getFinished(), event.getProgress(), event.getId()});
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    public ParserProgressEvent getActiveImport() throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject("select * from parser_progress where finished is null", PARSER_PROGRESS_ROWMAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }

    public ParserProgressEvent findLatestImport() throws DatabaseException {
        try {
            ParserProgressEvent event = getJdbcTemplate().queryForObject("select * from parser_progress where finished is not null order by date desc limit 1", PARSER_PROGRESS_ROWMAPPER);
            return event;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
        }
    }
}

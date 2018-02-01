package com.lancefallon.usermgmt.player.sql;

import com.lancefallon.usermgmt.player.messages.ParserProgressMessage;
import com.lancefallon.usermgmt.player.model.*;
import com.lancefallon.usermgmt.team.model.NflTeam;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public interface PlayerSql {

    //row cols
    String PLAYER_COL_ID = "id";
    String PLAYER_COL_RANK = "rank";
    String PLAYER_COL_NAME = "name";
    String PLAYER_COL_HEIGHT = "height";
    String PLAYER_COL_WEIGHT = "weight";
    String PLAYER_COL_POSITION_RANK = "position_rank";
    String PLAYER_COL_PROJECTED_ROUND = "projected_round";
    String PLAYER_COL_YEAR_CLASS = "year_class";
    String PLAYER_COL_YEAR = "year";
    String POSITION_COL_POSITION_ID = "position_id";
    String POSITION_COL_POSITION_NAME = "position_name";
    String POSITION_CATEGORY_COL_POSITIONCATEGORY_ID = "position_category_id";
    String POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME = "position_category_name";
    String POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID = "position_sideofball_id";
    String POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME = "position_sideofball_name";
    String COLLEGE_COL_COLLEGE_ID = "college_id";
    String COLLEGE_COL_COLLEGE_NAME = "college_name";
    String CONF_COL_CONFERENCE_ID = "conf_id";
    String CONF_COL_CONFERENCE_NAME = "conf_name";
    String PLAYER_COL_COLLEGE_TEXT = "college_text";
    String PLAYER_COL_SOURCE = "source";

    //combine properties
    String PLAYER_COL_FORTY_TEXT = "forty_yard_dash";
    String PLAYER_COL_BENCH_TEXT = "bench_press";
    String PLAYER_COL_VERTICAL_TEXT = "vertical_jump";
    String PLAYER_COL_BROARD_TEXT = "broad_jump";
    String PLAYER_COL_THREECONE_TEXT = "three_cone_drill";
    String PLAYER_COL_TWENTY_TEXT = "twenty_yard_shuttle";
    String PLAYER_COL_SIXTY_TEXT = "sixty_yard_shuttle";
    String PLAYER_COL_HANDS_TEXT = "hand_size";
    String PLAYER_COL_ARMS_TEXT = "arm_length";

    //draft pick props
    String PLAYER_COL_ROUND = "round";
    String PLAYER_COL_PICK = "pick";
    String PLAYER_COL_TEAM = "team";

    //sql
    String PLAYER_FIND_ALL = "SELECT rank, p.source, p.name, height, weight, position_rank, projected_round, year_class," +
            "p.id, year, p.position as position_id, p.college as college_id, college_text, c.name as college_name," +
            "conf.id as conf_id, conf.name as conf_name, pos.id as position_id, pos.name as position_name, " +
            "pc.id as position_category_id, pc.name as position_category_name, ps.id as position_sideofball_id, ps.name as position_sideofball_name, " +
            "p.forty_yard_dash, p.bench_press, p.vertical_jump, p.broad_jump, p.three_cone_drill, p.twenty_yard_shuttle, " +
            "p.sixty_yard_shuttle, p.hand_size, p.arm_length, p.round, p.pick, p.team " +
            "FROM player p " +
            "left outer join college c on p.college = c.id " +
            "left outer join conf on c.conf = conf.id " +
            "inner join position pos on p.position = pos.id " +
            "inner join position_category pc on pos.category = pc.id " +
            "inner join position_side_of_ball ps on pc.position_side_of_ball = ps.id where position_rank > 0";

    String PLAYER_FIND_ALL_WITH_NOTES = "SELECT array_agg((pnt.tag,t.name)) as tags, p.source, pn.summary, pn.strengths, pn.weaknesses, pn.likeness, pn.projected_round as notes_projected_round, pn.username, pn.id as notes_id, pn.overall_grade, rank, p.name, height, weight, position_rank, p.projected_round, year_class," +
            "p.id, year, p.position as position_id, p.college as college_id, college_text, c.name as college_name," +
            "conf.id as conf_id, conf.name as conf_name, pos.id as position_id, pos.name as position_name, " +
            "pc.id as position_category_id, pc.name as position_category_name, ps.id as position_sideofball_id, ps.name as position_sideofball_name, " +
            "p.forty_yard_dash, p.bench_press, p.vertical_jump, p.broad_jump, p.three_cone_drill, p.twenty_yard_shuttle, " +
            "p.sixty_yard_shuttle, p.hand_size, p.arm_length, p.round, p.pick, p.team " +
            "FROM player p " +
            "left outer join college c on p.college = c.id " +
            "left outer join conf on c.conf = conf.id " +
            "left outer join player_notes pn on p.id = pn.player and pn.username = ? " +
            "left outer join player_notes_tag pnt on pn.id = pnt.note " +
            "left outer join tag t on pnt.tag = t.id " +
            "inner join position pos on p.position = pos.id " +
            "inner join position_category pc on pos.category = pc.id " +
            "inner join position_side_of_ball ps on pc.position_side_of_ball = ps.id where position_rank > 0 " +
            "group by pn.summary, pn.strengths, pn.weaknesses, pn.likeness, notes_projected_round, pn.username, pn.id, pn.overall_grade, rank, p.name, height, weight, position_rank, p.projected_round, year_class, p.id, YEAR, p.position, p.college, college_text, c.name, conf.id, conf.name, pos.id, pos.name, pc.id, pc.name, ps.id, ps.name, p.forty_yard_dash, p.bench_press, p.vertical_jump, p.broad_jump, p.three_cone_drill, p.twenty_yard_shuttle, p.sixty_yard_shuttle, p.hand_size, p.arm_length, p.round, p.pick, p.team";

    String QUERY_YEARS = "select distinct year from player order by year;";

    String QUERY_POSITIONS = "select distinct pos.id, pos.name, ps.name as sideofballname from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name, pos.name;";

    String QUERY_POSITION_CATEGORIES = "select distinct cat.id, cat.name, ps.name as sideofballname from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name, cat.name;";

    String QUERY_POSITION_SIDES_OF_BALL = "select distinct ps.id, ps.name from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name;";

    String QUERY_COLLEGES = "select distinct c.id, c.name from player p inner join college c on p.college = c.id order by c.name;";

    String QUERY_CONFERENCES = "select distinct conf.id, conf.name from player p inner join college c on p.college = c.id inner join conf on c.conf = conf.id order by conf.name;";
    String NOTES_ID_COL = "notes_id";
    String NOTES_USERNAME_COL = "username";
    String NOTES_SUMMARY_COL = "summary";
    String NOTES_STRENGTHS_COL = "strengths";
    String NOTES_WEAKNESSES_COL = "weaknesses";
    String NOTES_LIKENESS_COL = "likeness";
    String NOTES_PROJECTEDROUND_COL = "notes_projected_round";
    String NOTES_OVERALLGRADE_COL = "overall_grade";


    RowMapper<Integer> YEARS_ROW_MAPPER = (rs, rowNum) -> {
        return rs.getInt("year");
    };

    RowMapper<Position> POSITION_ROW_MAPPER = (rs, rowNum) -> {
        return new Position(rs.getInt("id"), rs.getString("name"), null);
    };

    RowMapper<PositionCategory> POSITION_CATEGORIES_ROW_MAPPER = (rs, rowNum) -> {
        return new PositionCategory(rs.getInt("id"), rs.getString("name"), null);
    };

    RowMapper<PositionSideOfBall> POSITION_SIDEOFBALL_ROW_MAPPER = (rs, rowNum) -> {
        return new PositionSideOfBall(rs.getInt("id"), rs.getString("name"));
    };

    RowMapper<College> COLLEGE_ROW_MAPPER = (rs, rowNum) -> {
        return new College(rs.getInt("id"), rs.getString("name"), null);
    };

    RowMapper<Conference> CONFERENCE_ROW_MAPPER = (rs, rowNum) -> {
        return new Conference(rs.getInt("id"), rs.getString("name"));
    };

    //mappers
    RowMapper<Player> PLAYER_ROW_MAPPER = (rs, rowNum) -> {

        PositionSideOfBall positionSideOfBall = new PositionSideOfBall(rs.getInt(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID), rs.getString(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME));
        PositionCategory positionCategory = new PositionCategory(rs.getInt(POSITION_CATEGORY_COL_POSITIONCATEGORY_ID), rs.getString(POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME), positionSideOfBall);
        Position position = new Position(rs.getInt(POSITION_COL_POSITION_ID), rs.getString(POSITION_COL_POSITION_NAME), positionCategory);

        Conference conference = new Conference(rs.getInt(CONF_COL_CONFERENCE_ID), rs.getString(CONF_COL_CONFERENCE_NAME));
        College college = new College(rs.getInt(COLLEGE_COL_COLLEGE_ID), rs.getString(COLLEGE_COL_COLLEGE_NAME), conference);
        if (StringUtils.isEmpty(college.getName())) {
            college.setName(rs.getString(PLAYER_COL_COLLEGE_TEXT));
        }

        Player player = new Player();
        player.setId(rs.getInt(PLAYER_COL_ID));
        player.setRank(rs.getInt(PLAYER_COL_ID));
        player.setName(rs.getString(PLAYER_COL_NAME));
        player.setHeight(rs.getDouble(PLAYER_COL_HEIGHT));
        player.setWeight(rs.getDouble(PLAYER_COL_WEIGHT));
        player.setPositionRank(rs.getInt(PLAYER_COL_POSITION_RANK));
        player.setProjectedRound(rs.getString(PLAYER_COL_PROJECTED_ROUND));
        player.setYearClass(rs.getString(PLAYER_COL_YEAR_CLASS));
        player.setYear(rs.getInt(PLAYER_COL_YEAR));
        player.setCollegeText(rs.getString(PLAYER_COL_COLLEGE_TEXT));
        player.setPosition(position);
        player.setCollege(college);

        //combine properties
        player.setFortyYardDash(rs.getDouble(PLAYER_COL_FORTY_TEXT));
        player.setBenchPress(rs.getDouble(PLAYER_COL_BENCH_TEXT));
        player.setVerticalJump(rs.getDouble(PLAYER_COL_VERTICAL_TEXT));
        player.setBroadJump(rs.getDouble(PLAYER_COL_BROARD_TEXT));
        player.setThreeConeDrill(rs.getDouble(PLAYER_COL_THREECONE_TEXT));
        player.setTwentyYardShuttle(rs.getDouble(PLAYER_COL_TWENTY_TEXT));
        player.setSixtyYardShuttle(rs.getDouble(PLAYER_COL_SIXTY_TEXT));
        player.setHandSize(rs.getDouble(PLAYER_COL_HANDS_TEXT));
        player.setArmLength(rs.getDouble(PLAYER_COL_ARMS_TEXT));

        //draft props
        player.setRound(rs.getInt(PLAYER_COL_ROUND));
        player.setPick(rs.getInt(PLAYER_COL_PICK));
        player.setTeam(new NflTeam(rs.getString(PLAYER_COL_TEAM), "", "", "", "", "", ""));

        player.setSource(rs.getString(PLAYER_COL_SOURCE));

        return player;
    };

    RowMapper<Player> PLAYER_AND_NOTES_ROW_MAPPER = (rs, rowNum) -> {

        PositionSideOfBall positionSideOfBall = new PositionSideOfBall(rs.getInt(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID), rs.getString(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME));
        PositionCategory positionCategory = new PositionCategory(rs.getInt(POSITION_CATEGORY_COL_POSITIONCATEGORY_ID), rs.getString(POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME), positionSideOfBall);
        Position position = new Position(rs.getInt(POSITION_COL_POSITION_ID), rs.getString(POSITION_COL_POSITION_NAME), positionCategory);

        Conference conference = new Conference(rs.getInt(CONF_COL_CONFERENCE_ID), rs.getString(CONF_COL_CONFERENCE_NAME));
        College college = new College(rs.getInt(COLLEGE_COL_COLLEGE_ID), rs.getString(COLLEGE_COL_COLLEGE_NAME), conference);
        if (StringUtils.isEmpty(college.getName())) {
            college.setName(rs.getString(PLAYER_COL_COLLEGE_TEXT));
        }

        Player player = new Player();
        player.setId(rs.getInt(PLAYER_COL_ID));
        player.setRank(rs.getInt(PLAYER_COL_ID));
        player.setName(rs.getString(PLAYER_COL_NAME));
        player.setHeight(rs.getDouble(PLAYER_COL_HEIGHT));
        player.setWeight(rs.getDouble(PLAYER_COL_WEIGHT));
        player.setPositionRank(rs.getInt(PLAYER_COL_POSITION_RANK));
        player.setProjectedRound(rs.getString(PLAYER_COL_PROJECTED_ROUND));
        player.setYearClass(rs.getString(PLAYER_COL_YEAR_CLASS));
        player.setYear(rs.getInt(PLAYER_COL_YEAR));
        player.setCollegeText(rs.getString(PLAYER_COL_COLLEGE_TEXT));
        player.setPosition(position);
        player.setCollege(college);

        try {
            Player playerIdOnly = new Player();
            playerIdOnly.setId(player.getId());

            //map player tags
            List<NoteTag> tags = new ArrayList<>();
            Array tagsArray = rs.getArray("tags");
            if (tagsArray.getArray() != null) {
                for (Object tagString : (Object[]) tagsArray.getArray()) {

                    String[] parts = tagString.toString().substring(1, tagString.toString().length() - 1).split(",");
                    if (parts.length == 2 && !StringUtils.isEmpty(parts[0]) && !StringUtils.isEmpty(parts[1])) {
                        tags.add(new NoteTag(Integer.parseInt(parts[0].replaceAll("\"", "")), parts[1].replaceAll("\"", "")));
                    }
                }
            }

            PlayerNote playerNotes = new PlayerNote(rs.getInt(NOTES_ID_COL), rs.getString(NOTES_USERNAME_COL),
                    playerIdOnly, rs.getString(NOTES_SUMMARY_COL), rs.getString(NOTES_STRENGTHS_COL),
                    rs.getString(NOTES_WEAKNESSES_COL), rs.getInt(NOTES_LIKENESS_COL), rs.getInt(NOTES_PROJECTEDROUND_COL),
                    rs.getInt(NOTES_OVERALLGRADE_COL), tags);
            player.setNotes(playerNotes);
        } catch (Exception e) {
            e.getMessage();
        }

        //combine properties
        player.setFortyYardDash(rs.getDouble(PLAYER_COL_FORTY_TEXT));
        player.setBenchPress(rs.getDouble(PLAYER_COL_BENCH_TEXT));
        player.setVerticalJump(rs.getDouble(PLAYER_COL_VERTICAL_TEXT));
        player.setBroadJump(rs.getDouble(PLAYER_COL_BROARD_TEXT));
        player.setThreeConeDrill(rs.getDouble(PLAYER_COL_THREECONE_TEXT));
        player.setTwentyYardShuttle(rs.getDouble(PLAYER_COL_TWENTY_TEXT));
        player.setSixtyYardShuttle(rs.getDouble(PLAYER_COL_SIXTY_TEXT));
        player.setHandSize(rs.getDouble(PLAYER_COL_HANDS_TEXT));
        player.setArmLength(rs.getDouble(PLAYER_COL_ARMS_TEXT));

        //draft props
        player.setRound(rs.getInt(PLAYER_COL_ROUND));
        player.setPick(rs.getInt(PLAYER_COL_PICK));
        player.setTeam(new NflTeam(rs.getString(PLAYER_COL_TEAM), "", "", "", "", "", ""));

        player.setSource(rs.getString(PLAYER_COL_SOURCE));

        return player;
    };

    RowMapper<NoteTag> NOTE_TAG_ROW_MAPPER = (rs, rowNum) -> {

        NoteTag tag = new NoteTag();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));
        return tag;

    };

    RowMapper<ParserProgressMessage> PARSER_PROGRESS_ROWMAPPER = (rs, rowNum) -> {

        ParserProgressMessage event = new ParserProgressMessage();
        event.setId(rs.getString("id"));
        event.setUsername(rs.getString("username"));
        event.setDate(rs.getTimestamp("date"));
        event.setDescription(rs.getString("description"));
        event.setStarted(rs.getTimestamp("started"));
        event.setFinished(rs.getTimestamp("finished"));
        event.setProgress(rs.getLong("progress"));
        return event;

    };
}

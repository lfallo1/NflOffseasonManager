package com.lancefallon.usermgmt.player.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.College;
import com.lancefallon.usermgmt.player.model.Conference;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.model.PlayerNote;
import com.lancefallon.usermgmt.player.model.Position;
import com.lancefallon.usermgmt.player.model.PositionCategory;
import com.lancefallon.usermgmt.player.model.PositionSideOfBall;
import com.lancefallon.usermgmt.player.sql.PlayerSql;

@Service
public class PlayerRepository extends JdbcDaoSupport implements PlayerSql {

	public PlayerRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<Player> findAll(OAuth2Authentication auth) throws DatabaseException {
		try{
			return auth != null ? getJdbcTemplate().query(PLAYER_FIND_ALL_WITH_NOTES, new Object[]{auth.getName()}, PLAYER_AND_NOTES_ROW_MAPPER) 
					: getJdbcTemplate().query(PLAYER_FIND_ALL, PLAYER_ROW_MAPPER);
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<Position> allPositions() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_POSITIONS, POSITION_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<PositionCategory> allPositionCategories() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_POSITION_CATEGORIES, POSITION_CATEGORIES_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<PositionSideOfBall> allPositionSidesOfBall() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_POSITION_SIDES_OF_BALL, POSITION_SIDEOFBALL_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<College> allColleges() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_COLLEGES, COLLEGE_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<Conference> allConferences() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_CONFERENCES, CONFERENCE_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}
	
	public List<Integer> allYears() throws DatabaseException{
		try{
			return getJdbcTemplate().query(QUERY_YEARS, YEARS_ROW_MAPPER);

		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

	public void updateNotes(String username, PlayerNote notes) throws DatabaseException {
		try{
			getJdbcTemplate().update("update player_notes set summary=?, strengths=?, weakness=?,likeness=?,projected_round=?,overall_grade = ? where username = ? and id = ?", 
					new Object[]{notes.getSummary(), notes.getStrengths(), notes.getWeaknesses(), 
					notes.getLikeness(), notes.getProjectedRound(), 
					notes.getOverallGrade(), username, notes.getId()});
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

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

	public List<PlayerNote> getByUsername(String username) throws DatabaseException {
		try{
			return getJdbcTemplate().query("select * from player_notes where username = ?", new Object[]{username}, new RowMapper<PlayerNote>(){
	
				@Override
				public PlayerNote mapRow(ResultSet rs, int rowNum) throws SQLException {
					Player player = new Player();
					player.setId(rs.getInt("player"));
					return new PlayerNote(rs.getInt("id"), rs.getString("username"), player, rs.getString("summary"),
							rs.getString("strengths"),rs.getString("weaknesses"),rs.getInt("likeness"),
							rs.getInt("proected_round"),rs.getInt("overall_grade"));
				}
				
			});
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

	public void deleteNotes(String username, PlayerNote notes) throws DatabaseException {
		try{
			getJdbcTemplate().update("delete from player_notes where id = ? and username = ?", notes.getId(), username);
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

}

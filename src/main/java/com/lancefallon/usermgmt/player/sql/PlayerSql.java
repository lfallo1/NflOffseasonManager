package com.lancefallon.usermgmt.player.sql;

import org.springframework.jdbc.core.RowMapper;

import com.lancefallon.usermgmt.player.model.College;
import com.lancefallon.usermgmt.player.model.Conference;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.model.PlayerNote;
import com.lancefallon.usermgmt.player.model.Position;
import com.lancefallon.usermgmt.player.model.PositionCategory;
import com.lancefallon.usermgmt.player.model.PositionSideOfBall;

public interface PlayerSql {
	
	//row cols
	static final String PLAYER_COL_ID = "id";
	static final String PLAYER_COL_RANK = "rank";
	static final String PLAYER_COL_NAME = "name";
	static final String PLAYER_COL_HEIGHT = "height";
	static final String PLAYER_COL_WEIGHT = "weight";
	static final String PLAYER_COL_POSITION_RANK = "position_rank";
	static final String PLAYER_COL_PROJECTED_ROUND = "projected_round";
	static final String PLAYER_COL_YEAR_CLASS = "year_class";
	static final String PLAYER_COL_YEAR = "year";
	static final String POSITION_COL_POSITION_ID = "position_id";
	static final String POSITION_COL_POSITION_NAME = "position_name";
	static final String POSITION_CATEGORY_COL_POSITIONCATEGORY_ID = "position_category_id";
	static final String POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME = "position_category_name";
	static final String POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID = "position_sideofball_id";
	static final String POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME = "position_sideofball_name";
	static final String COLLEGE_COL_COLLEGE_ID = "college_id";
	static final String COLLEGE_COL_COLLEGE_NAME = "college_name";
	static final String CONF_COL_CONFERENCE_ID = "conf_id";
	static final String CONF_COL_CONFERENCE_NAME = "conf_name";
	static final String PLAYER_COL_COLLEGE_TEXT = "college_text";
	
	//sql
	static final String PLAYER_FIND_ALL = "SELECT rank, p.name, height, weight, position_rank, projected_round, year_class,"+ 
       "p.id, year, p.position as position_id, p.college as college_id, college_text, c.name as college_name,"+ 
       "conf.id as conf_id, conf.name as conf_name, pos.id as position_id, pos.name as position_name, "+
       "pc.id as position_category_id, pc.name as position_category_name, ps.id as position_sideofball_id, ps.name as position_sideofball_name "+
	  "FROM player p "+
	  "left outer join college c on p.college = c.id "+
	  "left outer join conf on c.conf = conf.id "+
	  "inner join position pos on p.position = pos.id "+
	  "inner join position_category pc on pos.category = pc.id "+
	  "inner join position_side_of_ball ps on pc.position_side_of_ball = ps.id where position_rank > 0";
	
	static final String PLAYER_FIND_ALL_WITH_NOTES = "SELECT pn.notes, pn.username, pn.id as notes_id, pn.grade, rank, p.name, height, weight, position_rank, projected_round, year_class,"+ 
		       "p.id, year, p.position as position_id, p.college as college_id, college_text, c.name as college_name,"+ 
		       "conf.id as conf_id, conf.name as conf_name, pos.id as position_id, pos.name as position_name, "+
		       "pc.id as position_category_id, pc.name as position_category_name, ps.id as position_sideofball_id, ps.name as position_sideofball_name "+
			  "FROM player p "+
			  "left outer join college c on p.college = c.id "+
			  "left outer join conf on c.conf = conf.id "+
			  "left outer join player_notes pn on p.id = pn.player and pn.username = ?" +
			  "inner join position pos on p.position = pos.id "+
			  "inner join position_category pc on pos.category = pc.id "+
			  "inner join position_side_of_ball ps on pc.position_side_of_ball = ps.id where position_rank > 0";
	
	static final String QUERY_YEARS = "select distinct year from player order by year;";

	static final String QUERY_POSITIONS ="select distinct pos.id, pos.name, ps.name as sideofballname from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name, pos.name;";

	static final String QUERY_POSITION_CATEGORIES ="select distinct cat.id, cat.name, ps.name as sideofballname from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name, cat.name;";

	static final String QUERY_POSITION_SIDES_OF_BALL ="select distinct ps.id, ps.name from player p inner join position pos on p.position = pos.id inner join position_category cat on pos.category = cat.id inner join position_side_of_ball ps on cat.position_side_of_ball = ps.id order by ps.name;";

	static final String QUERY_COLLEGES ="select distinct c.id, c.name from player p inner join college c on p.college = c.id order by c.name;";

	static final String QUERY_CONFERENCES = "select distinct conf.id, conf.name from player p inner join college c on p.college = c.id inner join conf on c.conf = conf.id order by conf.name;";
	static final String NOTES_ID_COL = "notes_id";
	static final String NOTES_USERNAME_COL = "username";
	static final String NOTES_NOTE_COL = "notes";
	static final String NOTES_GRADE_COL = "grade";
		
	RowMapper<Integer> YEARS_ROW_MAPPER = (rs, rowNum) ->{
		return rs.getInt("year");
	};
	
	RowMapper<Position> POSITION_ROW_MAPPER = (rs, rowNum) ->{
		return new Position(rs.getInt("id"), rs.getString("name"), null);
	};
	
	RowMapper<PositionCategory> POSITION_CATEGORIES_ROW_MAPPER = (rs, rowNum) ->{
		return new PositionCategory(rs.getInt("id"), rs.getString("name"), null);
	};
	
	RowMapper<PositionSideOfBall> POSITION_SIDEOFBALL_ROW_MAPPER = (rs, rowNum) ->{
		return new PositionSideOfBall(rs.getInt("id"), rs.getString("name"));
	};
	
	RowMapper<College> COLLEGE_ROW_MAPPER = (rs, rowNum) ->{
		return new College(rs.getInt("id"), rs.getString("name"), null);
	};
	
	RowMapper<Conference> CONFERENCE_ROW_MAPPER = (rs, rowNum) ->{
		return new Conference(rs.getInt("id"), rs.getString("name"));
	};
	
	//mappers
	RowMapper<Player> PLAYER_ROW_MAPPER = (rs, rowNum) -> {
		
		PositionSideOfBall positionSideOfBall = new PositionSideOfBall(rs.getInt(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID), rs.getString(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME));		
		PositionCategory positionCategory = new PositionCategory(rs.getInt(POSITION_CATEGORY_COL_POSITIONCATEGORY_ID), rs.getString(POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME), positionSideOfBall);
		Position position = new Position(rs.getInt(POSITION_COL_POSITION_ID),rs.getString(POSITION_COL_POSITION_NAME), positionCategory);
		
		Conference conference = new Conference(rs.getInt(CONF_COL_CONFERENCE_ID), rs.getString(CONF_COL_CONFERENCE_NAME));
		College college = new College(rs.getInt(COLLEGE_COL_COLLEGE_ID), rs.getString(COLLEGE_COL_COLLEGE_NAME), conference);
		
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
		return player;
	};
	
	RowMapper<Player> PLAYER_AND_NOTES_ROW_MAPPER = (rs, rowNum) -> {
		
		PositionSideOfBall positionSideOfBall = new PositionSideOfBall(rs.getInt(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_ID), rs.getString(POSITION_SIDEOFBALL_COL_POSITION_SIDEOFBALL_NAME));		
		PositionCategory positionCategory = new PositionCategory(rs.getInt(POSITION_CATEGORY_COL_POSITIONCATEGORY_ID), rs.getString(POSITION_CATEGORY_COL_POSITIONCATEGORY_NAME), positionSideOfBall);
		Position position = new Position(rs.getInt(POSITION_COL_POSITION_ID),rs.getString(POSITION_COL_POSITION_NAME), positionCategory);
		
		Conference conference = new Conference(rs.getInt(CONF_COL_CONFERENCE_ID), rs.getString(CONF_COL_CONFERENCE_NAME));
		College college = new College(rs.getInt(COLLEGE_COL_COLLEGE_ID), rs.getString(COLLEGE_COL_COLLEGE_NAME), conference);
		
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
		
		try{
			Player playerIdOnly = new Player();
			playerIdOnly.setId(player.getId());
			PlayerNote playerNotes = new PlayerNote(rs.getInt(NOTES_ID_COL), rs.getString(NOTES_USERNAME_COL), playerIdOnly, rs.getString(NOTES_NOTE_COL), rs.getInt(NOTES_GRADE_COL));		
			player.setNotes(playerNotes);
		} catch(Exception e){
			e.getMessage();
		}
		
		return player;
	};
}

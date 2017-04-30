package com.lancefallon.usermgmt.team.sql;

import org.springframework.jdbc.core.RowMapper;

import com.lancefallon.usermgmt.team.model.NflTeam;

public interface NflTeamSql {

	static final String NFLTEAM_COL_TEAM = "team";
	static final String NFLTEAM_COL_URL = "url";
	static final String NFLTEAM_COL_TEAMPAGE = "teampage";
	static final String NFLTEAM_COL_CITY = "city";
	static final String NFLTEAM_COL_NICKNAME = "nickname";
	static final String NFLTEAM_COL_CONFERENCE = "conference";
	static final String PNFLTEAM_COL_DIVISION = "division";
	
	static final String NFLTEAM_FIND_ALL = "SELECT * from nfl_team order by team";
		
	RowMapper<NflTeam> NFLTEAM_ROW_MAPPER = (rs, rowNum) ->{
		return new NflTeam(rs.getString(NFLTEAM_COL_TEAM),rs.getString(NFLTEAM_COL_URL),rs.getString(NFLTEAM_COL_TEAMPAGE),
				rs.getString(NFLTEAM_COL_CITY),rs.getString(NFLTEAM_COL_NICKNAME),rs.getString(NFLTEAM_COL_CONFERENCE),
				rs.getString(PNFLTEAM_COL_DIVISION));
	};
	
}

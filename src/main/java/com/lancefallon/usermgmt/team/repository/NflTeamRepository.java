package com.lancefallon.usermgmt.team.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.team.model.NflTeam;
import com.lancefallon.usermgmt.team.sql.NflTeamSql;

@Service
public class NflTeamRepository extends JdbcDaoSupport implements NflTeamSql {

	public NflTeamRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<NflTeam> findAll() throws DatabaseException {
		try{
			return getJdbcTemplate().query(NFLTEAM_FIND_ALL, NFLTEAM_ROW_MAPPER);
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

}

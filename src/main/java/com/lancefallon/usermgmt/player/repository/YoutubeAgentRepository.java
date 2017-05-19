package com.lancefallon.usermgmt.player.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.sql.PlayerSql;

@Service
public class YoutubeAgentRepository extends JdbcDaoSupport implements PlayerSql {

	public YoutubeAgentRepository(@Autowired @Qualifier("YoutubeAgent") DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<String> find() throws DatabaseException {
		return getJdbcTemplate().query("select * from video", new RowMapper<String>(){
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("title");
			}
		});
	}

}

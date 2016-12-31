package com.lancefallon.usermgmt.player.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.sql.PlayerSql;

@Service
public class PlayerRepository extends JdbcDaoSupport implements PlayerSql {
	
	public PlayerRepository(@Autowired @Qualifier("primary") DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<Player> findAll() {
		return getJdbcTemplate().query(PLAYER_FIND_ALL, PLAYER_ROW_MAPPER);
	}

}

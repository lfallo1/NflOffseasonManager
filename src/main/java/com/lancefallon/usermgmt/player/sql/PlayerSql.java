package com.lancefallon.usermgmt.player.sql;

import org.springframework.jdbc.core.RowMapper;

import com.lancefallon.usermgmt.player.model.Player;

public interface PlayerSql {
	
	//row cols
	//TODO
	
	//sql
	String PLAYER_FIND_ALL = "select * from public.player";
	
	//mappers
	RowMapper<Player> PLAYER_ROW_MAPPER = (rs, rowNum) -> {
		return new Player();
	};
}

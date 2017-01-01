package com.lancefallon.usermgmt.player.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> findAll(OAuth2Authentication auth) throws DatabaseException{
		List<Player> players = playerRepository.findAll(auth);
		return players;
	}
}

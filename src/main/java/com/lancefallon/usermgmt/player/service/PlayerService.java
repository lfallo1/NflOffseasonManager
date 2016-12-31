package com.lancefallon.usermgmt.player.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> findAll(){
		return playerRepository.findAll();
	}
}

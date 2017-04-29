package com.lancefallon.usermgmt.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lance.stringutil.StringService;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.service.PlayerService;

/**
 * players api endpoint
 * @author lancefallon
 *
 */
@RestController
@RequestMapping("api/players")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Player>> findAllPlayers(OAuth2Authentication auth) throws DatabaseException{
		List<Player> players = playerService.findAll(auth);
		StringService stringService = new StringService();
		players.get(0).setName(stringService.changeTo("Lance", "an", "TD"));
		return new ResponseEntity<>(players, HttpStatus.OK);
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public ResponseEntity<Player> dummy() throws DatabaseException{
		Player player = new Player();
		StringService stringService = new StringService();
		player.setName(stringService.changeTo("Lance", "an", "TD"));
		return new ResponseEntity<>(player, HttpStatus.OK);
	}
}

package com.lancefallon.usermgmt.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		return new ResponseEntity<>(playerService.findAll(auth), HttpStatus.OK);
	}
}

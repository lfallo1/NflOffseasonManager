package com.lancefallon.usermgmt.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
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
	public ResponseEntity<List<Player>> findAllPlayers(){
		ResponseEntity<List<Player>> response = new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
		return response;
	}
	 
	@RequestMapping(value="/auth/{username}", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') and @authService.isSelf(#auth, #username)")
	public ResponseEntity<List<Player>> findAllPlayersAuth(OAuth2Authentication auth, @PathVariable String username){
		CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth.getUserAuthentication();
		System.out.println(token.getUserPrivileges().getDefaultDB());
		return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
	}
}

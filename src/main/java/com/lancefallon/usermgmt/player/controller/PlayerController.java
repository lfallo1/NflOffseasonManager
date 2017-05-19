package com.lancefallon.usermgmt.player.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.repository.StudentsRepository;
import com.lancefallon.usermgmt.player.repository.YoutubeAgentRepository;
import com.lancefallon.usermgmt.player.service.OutputService;
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

	@Autowired
	private OutputService outputService;

	@Autowired
	private YoutubeAgentRepository youtubeAgentRepository;

	@Autowired
	private StudentsRepository studentsRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Player>> findAllPlayers(OAuth2Authentication auth) throws DatabaseException{
		return new ResponseEntity<>(playerService.findAll(auth), HttpStatus.OK);
	}
	
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public ResponseEntity<Map<String,String>> exportAllPlayers(OAuth2Authentication auth) throws DatabaseException, IllegalArgumentException, IllegalAccessException{
		return new ResponseEntity<>(Collections.singletonMap("response", this.outputService.outputToExcel(auth)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/youtube", method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<String>>> findYoutubeAgentVideos(OAuth2Authentication auth) throws DatabaseException{
		Map<String, List<String>> response = new HashMap<>();
		response.put("response", youtubeAgentRepository.find());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/students", method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<String>>> findStudentNames() throws DatabaseException{
		Map<String, List<String>> response = new HashMap<>();
		response.put("response", studentsRepository.find());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

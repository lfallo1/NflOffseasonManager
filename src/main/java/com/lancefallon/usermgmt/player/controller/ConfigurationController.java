package com.lancefallon.usermgmt.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Config;
import com.lancefallon.usermgmt.player.service.ConfigurationService;

@RestController
@RequestMapping("api/config")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Config> loadConfig() throws DatabaseException{
		return new ResponseEntity<>(configurationService.loadConfiguration(), HttpStatus.OK);
	}
	
}

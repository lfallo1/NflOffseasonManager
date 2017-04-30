package com.lancefallon.usermgmt.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Config;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;
import com.lancefallon.usermgmt.team.repository.NflTeamRepository;

@Service
public class ConfigurationService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private NflTeamRepository nflTeamRepository;
	
	public Config loadConfiguration() throws DatabaseException{
		Config config = new Config();
		config.setColleges(this.playerRepository.allColleges());
		config.setConferences(this.playerRepository.allConferences());
		config.setPositions(this.playerRepository.allPositions());
		config.setPositionCategories(this.playerRepository.allPositionCategories());
		config.setPositionSidesOfBall(this.playerRepository.allPositionSidesOfBall());
		config.setYears(this.playerRepository.allYears());
		config.setNflTeams(this.nflTeamRepository.findAll());
		return config;
	}
	
}

package com.lancefallon.usermgmt.player.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.PlayerNote;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;

@Service
public class PlayerNotesService {

	@Autowired
	private PlayerRepository playerRepository;
	
	public Integer addOrUpdate(String username, PlayerNote notes) throws DatabaseException {
		if(notes.getId() != null){
			this.playerRepository.updateNotes(username, notes);
			return notes.getId();
		}
		return this.playerRepository.addNotes(username, notes);
	}

	public List<PlayerNote> getNotes(String username) throws DatabaseException {
		return this.playerRepository.getByUsername(username);
	}

	public void delete(String username, PlayerNote notes) throws DatabaseException {
		this.playerRepository.deleteNotes(username, notes);
	}

	
	
}
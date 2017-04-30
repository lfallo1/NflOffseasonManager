package com.lancefallon.usermgmt.player.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.exception.model.InvalidUserInputException;
import com.lancefallon.usermgmt.player.model.PlayerNote;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;

@Service
public class PlayerNotesService {

	@Autowired
	private PlayerRepository playerRepository;
	
	public Integer addOrUpdate(String username, PlayerNote notes) throws DatabaseException, InvalidUserInputException {
		
		if(notes.getOverallGrade() == null || notes.getOverallGrade() < 0 || notes.getOverallGrade() > 100){
			throw new InvalidUserInputException(new CustomErrorMessage("user_input", "Grade must be between 0 and 100"));
		}
		
		if(notes.getLikeness() != null && (notes.getLikeness() < 1 || notes.getLikeness() > 5)){
			throw new InvalidUserInputException(new CustomErrorMessage("user_input", "Likeness must be between 1 and 5"));
		}
		
		if(notes.getProjectedRound() != null && (notes.getProjectedRound() < 0 || notes.getProjectedRound() > 7)){
			throw new InvalidUserInputException(new CustomErrorMessage("user_input", "Likeness must be between 0 and 7"));
		}
		
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

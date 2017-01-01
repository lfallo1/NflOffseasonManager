package com.lancefallon.usermgmt.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.PlayerNote;
import com.lancefallon.usermgmt.player.service.PlayerNotesService;

@RestController
@RequestMapping("api/notes")
public class NotesController {
	
	@Autowired
	private PlayerNotesService playerNotesService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PlayerNote>> getNotes(OAuth2Authentication auth) throws DatabaseException{
		return new ResponseEntity<>(this.playerNotesService.getNotes(auth.getName()), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@PreAuthorize("@authService.isSelf(#auth, #notes.username)")
	public ResponseEntity<Integer> addOrUpdateNote(OAuth2Authentication auth, @RequestBody PlayerNote notes) throws DatabaseException{
		return new ResponseEntity<>(this.playerNotesService.addOrUpdate(auth.getName(), notes), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@PreAuthorize("@authService.isSelf(#auth, #notes.username)")
	public ResponseEntity<Void> deleteNote(OAuth2Authentication auth, @RequestBody PlayerNote notes) throws DatabaseException{
		this.playerNotesService.delete(auth.getName(), notes);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

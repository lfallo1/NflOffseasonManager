package com.lancefallon.usermgmt.sharing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserShareDto;
import com.lancefallon.usermgmt.sharing.service.UserSharePlayerService;

/**
 * players api endpoint
 * @author lancefallon
 *
 */
@RestController
@RequestMapping("api/share")
public class UserSharePlayerController {

	@Autowired
	private UserSharePlayerService userSharePlayerService;

	/**
	 * share a one or more players with one or more other "friended" users
	 * @param auth
	 * @param userShareDto
	 * @return
	 * @throws DatabaseException
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> share(OAuth2Authentication auth, UserShareDto userShareDto) throws DatabaseException{
		this.userSharePlayerService.share(auth, userShareDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * send a new friend request
	 * @param auth
	 * @param request
	 */
	@RequestMapping(value="friendRequest", method=RequestMethod.POST)
	public ResponseEntity<Void> sendFriendRequest(OAuth2Authentication auth, UserFriend request){
		this.userSharePlayerService.sendFriendRequest(auth, request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * respond to a friend request
	 * @param auth
	 * @param request
	 */
	@RequestMapping(value="/friendRequest", method=RequestMethod.PUT)
	public ResponseEntity<Void> friendRequestResponse(OAuth2Authentication auth, UserFriend request){
		this.userSharePlayerService.friendRequestResponse(auth, request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

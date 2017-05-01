package com.lancefallon.usermgmt.sharing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.sharing.model.SharedPlayersDashboardRequestDto;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserShareDto;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayer;
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

	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public ResponseEntity<List<UserFriend>> findFriends(OAuth2Authentication auth) throws DatabaseException{
		return new ResponseEntity<>(this.userSharePlayerService.getFriends(auth), HttpStatus.OK);
	}
	
	/**
	 * share a one or more players with one or more other "friended" users
	 * @param auth
	 * @param userShareDto
	 * @return
	 * @throws DatabaseException
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> share(OAuth2Authentication auth, @RequestBody UserShareDto userShareDto) throws DatabaseException{
		this.userSharePlayerService.share(auth, userShareDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * get players shared to logged in user since the specified time range.
	 * the request can include a min date, otherwise defaults to 2 weeks back.
	 * the request can include a wait time in seconds. it will loop in two second intervals for the specified wait time or until a new shared player is returned
	 * @param auth
	 * @param fromDate
	 * @return
	 * @throws DatabaseException
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="/load", method=RequestMethod.POST)
	public ResponseEntity<List<UserSharePlayer>> getSharedPlayers(OAuth2Authentication auth, @RequestBody SharedPlayersDashboardRequestDto dto) throws DatabaseException, InterruptedException{
		return new ResponseEntity<>(this.userSharePlayerService.getSharedPlayers(auth, dto), HttpStatus.OK);
	}
	
	@RequestMapping(value="/setviewed", method=RequestMethod.POST)
	public ResponseEntity<Void> setSharedPlayersViewed(OAuth2Authentication auth, @RequestBody List<UserSharePlayer> list) throws DatabaseException, InterruptedException{
		this.userSharePlayerService.setSharedPlayersViewed(auth, list);
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

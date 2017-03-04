package com.lancefallon.usermgmt.sharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.security.model.UserPrivileges;
import com.lancefallon.usermgmt.config.security.repository.UserRepository;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserShareDto;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayerDto;

@Service
public class UserSharePlayerService {
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private UserRepository userRepository;

	public void share(OAuth2Authentication auth, UserShareDto userShareDto) {
		for(String username : userShareDto.getUsernames()){
			UserPrivileges user = userRepository.tryLogin(username);
			
			//TODO validate & conditionally continue into for-loop
			//validation: are users friends? is player already shared?
			//for both checks, must check both combinations of sender / receiver
			
			for(UserSharePlayerDto player : userShareDto.getPlayers()){
				
				//TODO some sanity checks / validation before sending text message
				//does user have a phone number on record?
				smsService.sendTextMessage(user.getPhone(), player.getNotes(), player.getMediaUrl());
				
				//TODO add entry to database
			}
		}
		
	}
	
	public void friendRequestResponse(OAuth2Authentication auth, UserFriend request){
		//TODO update user_friend table in database
	}
	
	public void sendFriendRequest(OAuth2Authentication auth, UserFriend request){
		//TODO validate & add new entry to user_friend table
		//does an entry for these users already exist (need to check both combinations of sender / receiver)
	}

}

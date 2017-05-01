package com.lancefallon.usermgmt.sharing.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.sharing.model.SharedPlayersDashboardRequestDto;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserShareDto;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayer;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayerDto;
import com.lancefallon.usermgmt.sharing.repository.UserShareRepository;

@Service
public class UserSharePlayerService {
	
	@Autowired
	private UserShareRepository userShareRepository;

	public void share(OAuth2Authentication auth, UserShareDto userShareDto) throws DatabaseException {
		for(String receiverUsername : userShareDto.getUsernames()){
			
			//TODO check if receiverUsername and logged in username are friends
			
			for(UserSharePlayerDto player : userShareDto.getPlayers()){
				
				//TODO SMS Messages - maybe add in future
//				smsService.sendTextMessage(user.getPhone(), player.getNotes(), player.getMediaUrl());
				
				this.userShareRepository.sharePlayer(auth.getName(), receiverUsername, player);
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
	
	public List<UserFriend> getFriends(OAuth2Authentication auth) throws DatabaseException{
		return this.userShareRepository.findFriends(auth);
	}

	public List<UserSharePlayer> getSharedPlayers(OAuth2Authentication auth, SharedPlayersDashboardRequestDto dto) throws DatabaseException, InterruptedException {
		
		//get the from date parameter, either using the passed in request param or selecting the default of two weeks back
		Calendar defaultDateObject = Calendar.getInstance();
		defaultDateObject.add(Calendar.HOUR, -24*14);
		Date defaultDate = defaultDateObject.getTime();
		Date dateParam = dto.getFromDate() != null ? dto.getFromDate() : defaultDate;
		
		//if there is a wait time, then poll every 2 seconds.  this allows the front end to limit the polling calls
		if(dto.getWaitTimeSeconds() > 0){
			
			//if polling, always going to want to exclude old / already viewed entries
			dto.setHasViewed(false);
			
			//set target time in future (max of two minutes)
			Calendar untilTime = Calendar.getInstance();
			untilTime.add(Calendar.SECOND, Math.min(dto.getWaitTimeSeconds(), 120));
			List<UserSharePlayer> sharedList = new ArrayList<>();
			boolean found = false;
			
			//loop until found or a new result returned
			while(Calendar.getInstance().getTimeInMillis() < untilTime.getTimeInMillis() && !found){
				sharedList = this.userShareRepository.getSharedPlayers(auth.getName(), dateParam, dto.getHasViewed());
				found = sharedList.size() > 0;
				Thread.sleep(2000);
			}
			return sharedList;
		} else{
			//if wait time not set, then just make one query
			return this.userShareRepository.getSharedPlayers(auth.getName(), dateParam, dto.getHasViewed());
		}
	}
	
	/**
	 * flip has viewed flag to true for the list of UserSharePlayer objects
	 * @param auth
	 * @param list
	 */
	public void setSharedPlayersViewed(OAuth2Authentication auth, List<UserSharePlayer> list){
		this.userShareRepository.setSharedPlayersViewed(auth.getName(), list);
	}

}

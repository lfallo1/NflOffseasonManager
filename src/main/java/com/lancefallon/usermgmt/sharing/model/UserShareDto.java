package com.lancefallon.usermgmt.sharing.model;

import java.util.List;

/**
 * a user share dto object with a list of users with whom the logged in user wants to share
 * a list of players with
 * @author lancefallon
 *
 */
public class UserShareDto {
	
	List<String> usernames;
	List<UserSharePlayerDto> players;
	
	public UserShareDto(){}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public List<UserSharePlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<UserSharePlayerDto> players) {
		this.players = players;
	}
	
	

}

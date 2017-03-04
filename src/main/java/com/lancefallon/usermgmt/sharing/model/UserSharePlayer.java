package com.lancefallon.usermgmt.sharing.model;

import java.sql.Time;

import com.lancefallon.usermgmt.player.model.Player;

/**
 * object representing a player shared by a user with another user
 * @author lancefallon
 *
 */
public class UserSharePlayer {

	private Integer id;
	private String usernameSender;
	private String usernameReceiver;
	private Boolean hasViewed;
	private Time date;
	private Player player;
	private String messageBody;
	private String messageMediaUrl;

	public UserSharePlayer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsernameSender() {
		return usernameSender;
	}

	public void setUsernameSender(String usernameSender) {
		this.usernameSender = usernameSender;
	}

	public String getUsernameReceiver() {
		return usernameReceiver;
	}

	public void setUsernameReceiver(String usernameReceiver) {
		this.usernameReceiver = usernameReceiver;
	}

	public Boolean getHasViewed() {
		return hasViewed;
	}

	public void setHasViewed(Boolean hasViewed) {
		this.hasViewed = hasViewed;
	}

	public Time getDate() {
		return date;
	}

	public void setDate(Time date) {
		this.date = date;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageMediaUrl() {
		return messageMediaUrl;
	}

	public void setMessageMediaUrl(String messageMediaUrl) {
		this.messageMediaUrl = messageMediaUrl;
	}

}

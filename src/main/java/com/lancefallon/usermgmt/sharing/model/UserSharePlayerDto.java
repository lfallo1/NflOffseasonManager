package com.lancefallon.usermgmt.sharing.model;

/**
 * a dto representing an individual player (with notes / media).
 * this object is included in the UserShareDto object
 * @author lancefallon
 *
 */
public class UserSharePlayerDto {
	
	private Integer playerId;
	private String notes;
	private String mediaUrl;
	
	public UserSharePlayerDto(){}
	
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	
	

}

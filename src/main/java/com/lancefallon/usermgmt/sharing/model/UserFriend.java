package com.lancefallon.usermgmt.sharing.model;

/**
 * represents a "friend" object (a user who initiated a friend request, and the "friend").
 * the state of this object can be pending a response, accepted, or declined
 * @author lancefallon
 *
 */
public class UserFriend {

	private String user;
	private String friend;
	private Boolean pendingResponse;
	private Boolean acceptedRequest;

	public UserFriend() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	public Boolean getPendingResponse() {
		return pendingResponse;
	}

	public void setPendingResponse(Boolean pendingResponse) {
		this.pendingResponse = pendingResponse;
	}

	public Boolean getAcceptedRequest() {
		return acceptedRequest;
	}

	public void setAcceptedRequest(Boolean acceptedRequest) {
		this.acceptedRequest = acceptedRequest;
	}

}

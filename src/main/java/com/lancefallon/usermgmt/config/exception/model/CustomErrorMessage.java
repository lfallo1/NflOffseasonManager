package com.lancefallon.usermgmt.config.exception.model;

public class CustomErrorMessage {
	private String key;
	private String message;
	
	public CustomErrorMessage(){}
	
	public CustomErrorMessage(String key, String message) {
		this.key = key;
		this.message = message;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

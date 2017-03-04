package com.lancefallon.usermgmt.player.model;

public class PlayerNote {

	private Integer id;
	private String username;
	private Player player;
	private String notes;
	private Integer grade;

	public PlayerNote() {
	}

	public PlayerNote(Integer id, String username, Player player, String notes, Integer grade) {
		this.id = id;
		this.username = username;
		this.player = player;
		this.notes = notes;
		this.grade = grade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}

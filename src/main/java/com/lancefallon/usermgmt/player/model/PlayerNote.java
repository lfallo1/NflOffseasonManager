package com.lancefallon.usermgmt.player.model;

public class PlayerNote {

	private Integer id;
	private String username;
	private Player player;
	private String summary;
	private String strengths;
	private String weaknesses;
	private Integer likeness; // 1-5
	private Integer projectedRound; // 0-7, where 0 is UDFA
	private Integer overallGrade; // 0-100

	public PlayerNote() {
	}

	public PlayerNote(Integer id, String username, Player player, String summary, String strengths, String weaknesses,
			Integer likeness, Integer projectedRound, Integer overallGrade) {
		this.id = id;
		this.username = username;
		this.player = player;
		this.summary = summary;
		this.strengths = strengths;
		this.weaknesses = weaknesses;
		this.likeness = likeness;
		this.projectedRound = projectedRound;
		this.overallGrade = overallGrade;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStrengths() {
		return strengths;
	}

	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}

	public String getWeaknesses() {
		return weaknesses;
	}

	public void setWeaknesses(String weaknesses) {
		this.weaknesses = weaknesses;
	}

	public Integer getLikeness() {
		return likeness;
	}

	public void setLikeness(Integer likeness) {
		this.likeness = likeness;
	}

	public Integer getProjectedRound() {
		return projectedRound;
	}

	public void setProjectedRound(Integer projectedRound) {
		this.projectedRound = projectedRound;
	}

	public Integer getOverallGrade() {
		return overallGrade;
	}

	public void setOverallGrade(Integer overallGrade) {
		this.overallGrade = overallGrade;
	}

}

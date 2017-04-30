package com.lancefallon.usermgmt.player.model;

import com.lancefallon.usermgmt.team.model.NflTeam;

public class Player {

	private Integer id;
	private Integer rank;
	private String name;
	private College college;
	private String collegeText;
	private Position position;
	private Double height;
	private Double weight;
	private Integer positionRank;
	private String projectedRound;
	private String yearClass;
	private Integer year;

	// workout properties
	private Double fortyYardDash;
	private Double benchPress;
	private Double verticalJump;
	private Double broadJump;
	private Double threeConeDrill;
	private Double twentyYardShuttle;
	private Double sixtyYardShuttle;
	private Double handSize;
	private Double armLength;

	// draft props
	private Integer round;
	private Integer pick;
	private NflTeam team;

	private PlayerNote notes;

	public Player() {
		this.rank = 0;
	}

	public Player(Integer id, Integer rank, String name, College college, String collegeText, Position position,
			Double height, Double weight, Integer positionRank, String projectedRound, String yearClass, Integer year) {
		this.id = id;
		this.rank = rank;
		this.name = name;
		this.college = college;
		this.collegeText = collegeText;
		this.position = position;
		this.height = height;
		this.weight = weight;
		this.positionRank = positionRank;
		this.projectedRound = projectedRound;
		this.yearClass = yearClass;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public String getCollegeText() {
		return collegeText;
	}

	public void setCollegeText(String collegeText) {
		this.collegeText = collegeText;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getPositionRank() {
		return positionRank;
	}

	public void setPositionRank(Integer positionRank) {
		this.positionRank = positionRank;
	}

	public String getProjectedRound() {
		return projectedRound;
	}

	public void setProjectedRound(String projectedRound) {
		this.projectedRound = projectedRound;
	}

	public String getYearClass() {
		return yearClass;
	}

	public void setYearClass(String yearClass) {
		this.yearClass = yearClass;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public PlayerNote getNotes() {
		return notes;
	}

	public void setNotes(PlayerNote notes) {
		this.notes = notes;
	}

	public Double getFortyYardDash() {
		return fortyYardDash;
	}

	public void setFortyYardDash(Double fortyYardDash) {
		this.fortyYardDash = fortyYardDash;
	}

	public Double getBenchPress() {
		return benchPress;
	}

	public void setBenchPress(Double benchPress) {
		this.benchPress = benchPress;
	}

	public Double getVerticalJump() {
		return verticalJump;
	}

	public void setVerticalJump(Double verticalJump) {
		this.verticalJump = verticalJump;
	}

	public Double getBroadJump() {
		return broadJump;
	}

	public void setBroadJump(Double broadJump) {
		this.broadJump = broadJump;
	}

	public Double getThreeConeDrill() {
		return threeConeDrill;
	}

	public void setThreeConeDrill(Double threeConeDrill) {
		this.threeConeDrill = threeConeDrill;
	}

	public Double getTwentyYardShuttle() {
		return twentyYardShuttle;
	}

	public void setTwentyYardShuttle(Double twentyYardShuttle) {
		this.twentyYardShuttle = twentyYardShuttle;
	}

	public Double getSixtyYardShuttle() {
		return sixtyYardShuttle;
	}

	public void setSixtyYardShuttle(Double sixtyYardShuttle) {
		this.sixtyYardShuttle = sixtyYardShuttle;
	}

	public Double getHandSize() {
		return handSize;
	}

	public void setHandSize(Double handSize) {
		this.handSize = handSize;
	}

	public Double getArmLength() {
		return armLength;
	}

	public void setArmLength(Double armLength) {
		this.armLength = armLength;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getPick() {
		return pick;
	}

	public void setPick(Integer pick) {
		this.pick = pick;
	}

	public NflTeam getTeam() {
		return team;
	}

	public void setTeam(NflTeam team) {
		this.team = team;
	}

}

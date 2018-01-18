package com.lancefallon.usermgmt.player.model;

import java.util.List;

import com.lancefallon.usermgmt.team.model.NflTeam;

public class Config {

	private List<Integer> years;
	private List<Position> positions;
	private List<PositionCategory> positionCategories;
	private List<PositionSideOfBall> positionSidesOfBall;
	private List<College> colleges;
	private List<Conference> conferences;
	private List<NflTeam> nflTeams;
	private List<NoteTag> tags;

	public Config() {
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public List<PositionCategory> getPositionCategories() {
		return positionCategories;
	}

	public void setPositionCategories(List<PositionCategory> positionCategories) {
		this.positionCategories = positionCategories;
	}

	public List<PositionSideOfBall> getPositionSidesOfBall() {
		return positionSidesOfBall;
	}

	public void setPositionSidesOfBall(List<PositionSideOfBall> positionSidesOfBall) {
		this.positionSidesOfBall = positionSidesOfBall;
	}

	public List<College> getColleges() {
		return colleges;
	}

	public void setColleges(List<College> colleges) {
		this.colleges = colleges;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}

	public List<NflTeam> getNflTeams() {
		return nflTeams;
	}

	public void setNflTeams(List<NflTeam> nflTeams) {
		this.nflTeams = nflTeams;
	}

	public List<NoteTag> getTags() {
		return tags;
	}

	public void setTags(List<NoteTag> tags) {
		this.tags = tags;
	}
}

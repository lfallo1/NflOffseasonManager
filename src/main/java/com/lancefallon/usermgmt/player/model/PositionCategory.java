package com.lancefallon.usermgmt.player.model;

public class PositionCategory {

	private Integer id;
	private String name;
	private PositionSideOfBall positionSideOfBall;

	public PositionCategory(Integer id, String name, PositionSideOfBall positionSideOfBall) {
		this.id = id;
		this.name = name;
		this.positionSideOfBall = positionSideOfBall;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PositionSideOfBall getPositionSideOfBall() {
		return positionSideOfBall;
	}

	public void setPositionSideOfBall(PositionSideOfBall positionSideOfBall) {
		this.positionSideOfBall = positionSideOfBall;
	}

}

package com.lancefallon.usermgmt.player.model;

public class College {

	private Integer id;
	private Conference conf;
	private String name;

	public College() {
	}

	public College(Integer id, String name, Conference conf) {
		this.id = id;
		this.conf = conf;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Conference getConf() {
		return conf;
	}

	public void setConf(Conference conf) {
		this.conf = conf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

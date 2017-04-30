package com.lancefallon.usermgmt.team.model;

public class NflTeam {

	private String team;
	private String url;
	private String teampage;
	private String city;
	private String nickname;
	private String conference;
	private String division;
	
	public NflTeam(){}
	
	public NflTeam(String team, String url, String teampage, String city, String nickname, String conference,
			String division) {
		this.team = team;
		this.url = url;
		this.teampage = teampage;
		this.city = city;
		this.nickname = nickname;
		this.conference = conference;
		this.division = division;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTeampage() {
		return teampage;
	}
	public void setTeampage(String teampage) {
		this.teampage = teampage;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getConference() {
		return conference;
	}
	public void setConference(String conference) {
		this.conference = conference;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	
	
}

package com.lancefallon.usermgmt.common.email.model;

import java.util.List;

import com.lancefallon.usermgmt.player.model.Player;

/**
 * Email config for user related emails
 * @author lancefallon
 *
 */
public class EmailConfigPlayers extends EmailConfig {

	private List<Player> players;

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}

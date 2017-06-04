package com.lancefallon.usermgmt.config.actuator.endpoints;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import com.lancefallon.usermgmt.player.model.Player;

@Component
public class CustonAppEndpoint extends AbstractEndpoint<Player> {

	public CustonAppEndpoint() {
		super("customAppEndpoint", false);
	}

	@Override
	public Player invoke() {
		Player player = new Player();
		player.setName("Goddess");
		return player;
	}

}

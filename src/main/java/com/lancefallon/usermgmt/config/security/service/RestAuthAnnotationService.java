package com.lancefallon.usermgmt.config.security.service;

import com.lancefallon.usermgmt.player.model.Player;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component("authService")
public class RestAuthAnnotationService {
	
	public Boolean isSelf(OAuth2Authentication auth, String username){
		if(auth == null){
			return false;
		}
		return username.equalsIgnoreCase(auth.getName());
	}

	public Boolean checkPlayerId(Integer playerId, Player player){
		return player != null && player.getId() != null && player.getId().equals(playerId);
	}
}

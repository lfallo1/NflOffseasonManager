package com.lancefallon.usermgmt.player.service;

import com.lancefallon.usermgmt.config.events.ParserProgressEvent;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findAll(OAuth2Authentication auth) throws DatabaseException {
        List<Player> players = playerRepository.findAll(auth);
        return players;
    }

    public Player addPlayer(Player player) throws DatabaseException {
        Integer id = this.playerRepository.addPlayer(player);
        player.setId(id);
        return player;
    }

    public Boolean updatePlayer(Player player) throws DatabaseException {
        return this.playerRepository.updatePlayer(player) > 0;
    }

    public Player getPlayerById(Integer playerId) throws DatabaseException {
        return this.playerRepository.findPlayerById(playerId);
    }

    public ParserProgressEvent findActiveImport() throws DatabaseException {
        return this.playerRepository.getActiveImport();
    }

    public ParserProgressEvent findLatestImport() throws DatabaseException {
        return this.playerRepository.findLatestImport();
    }
}

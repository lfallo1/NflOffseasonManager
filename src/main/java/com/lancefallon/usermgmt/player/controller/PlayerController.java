package com.lancefallon.usermgmt.player.controller;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * players api endpoint
 *
 * @author lancefallon
 */
@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Player>> findAllPlayers(Authentication authentication) throws DatabaseException {
        OAuth2Authentication auth = (OAuth2Authentication) authentication;
        return new ResponseEntity<>(playerService.findAll(auth), HttpStatus.OK);
    }

    //admin endpoints

    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer playerId) throws DatabaseException {
        return new ResponseEntity<>(this.playerService.getPlayerById(playerId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) throws DatabaseException {
        return new ResponseEntity<>(this.playerService.addPlayer(player), HttpStatus.OK);
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> updatePlayer(@PathVariable Integer playerId, @RequestBody Player player) throws DatabaseException {
        return new ResponseEntity<>(this.playerService.updatePlayer(player), HttpStatus.OK);
    }
}

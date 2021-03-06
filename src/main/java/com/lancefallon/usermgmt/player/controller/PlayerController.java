package com.lancefallon.usermgmt.player.controller;

import com.google.common.collect.ImmutableMap;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.model.AppProperties;
import com.lancefallon.usermgmt.player.messages.ParserProgressMessage;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.service.PlayerService;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * players api endpoint
 *
 * @author lancefallon
 */
@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("parserEventExchange")
    private Exchange exchange;

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

    @RequestMapping(value = "/draft/{playerId}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') && @authService.checkPlayerId(#playerId, #player)")
    public ResponseEntity<Boolean> updateDraftInformation(@PathVariable Integer playerId, @RequestBody Player player) throws DatabaseException {
        return new ResponseEntity<>(this.playerService.updateDraftInformation(player), HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh/{datasourceTypeId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> refreshDataSource(@PathVariable Integer datasourceTypeId) throws DatabaseException {

        try {

            if (this.playerService.findActiveImport() == null) {

                //check status of import service
                new RestTemplate().getForEntity(this.appProperties.getImportHost() + "health", String.class);

                rabbitTemplate.convertAndSend(exchange.getName(), "nflcombine.refresh.start", datasourceTypeId);
                return new ResponseEntity<>(ImmutableMap.of("status", "Import started"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ImmutableMap.of("status", "Import already in progress"), HttpStatus.BAD_REQUEST);

        } catch (RestClientException e) {
            return new ResponseEntity<>(ImmutableMap.of("status", "Importer is not accessible"), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/refresh-active", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParserProgressMessage> findActiveImport() throws DatabaseException {
        ParserProgressMessage event = this.playerService.findActiveImport();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh-latest", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParserProgressMessage> findLatestImport() throws DatabaseException {
        return new ResponseEntity<>(this.playerService.findLatestImport(), HttpStatus.OK);
    }
}

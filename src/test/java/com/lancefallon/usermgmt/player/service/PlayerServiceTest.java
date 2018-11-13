package com.lancefallon.usermgmt.player.service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import testhelpers.DomainFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;

    @Test
    void findAll() throws DatabaseException {

        Mockito.when(playerRepository.findAll(null)).thenReturn(DomainFactory.createPlayers(4));

        List<Player> players = this.playerService.findAll(null);
        assertEquals(DomainFactory.createPlayers(4).size(), players.size());
    }

    @Test
    void addPlayer() throws DatabaseException {
        Player player = DomainFactory.createPlayers(1).get(0);
        int id = 99;
        Mockito.when(playerRepository.addPlayer(player)).thenReturn(id);

        Player result = this.playerService.addPlayer(player);
        assert (id == result.getId());
    }

    @Test
    void updatePlayer() throws DatabaseException {
        Mockito.when(playerRepository.updatePlayer(Mockito.any(Player.class))).thenReturn(1);
        Boolean result = this.playerService.updatePlayer(DomainFactory.createPlayers(1).get(0));
        assertTrue(result);
    }

    @Test
    void updateDraftInformation() throws DatabaseException {
        Mockito.when(playerRepository.updateDraftInformation(Mockito.any(Player.class))).thenReturn(1);
        Boolean result = this.playerService.updateDraftInformation(DomainFactory.createPlayers(1).get(0));
        assertTrue(result);
    }

    @Test
    void getPlayerById() throws DatabaseException {
        int id = 99;
        Player player = DomainFactory.createPlayers(1).get(0);
        player.setId(id);
        Mockito.when(playerRepository.findPlayerById(Mockito.anyInt())).thenReturn(player);

        Player result = this.playerService.getPlayerById(id);

        assert (id == result.getId());
    }

    @Test
    void findActiveImport() {
    }

    @Test
    void findLatestImport() {
    }


}
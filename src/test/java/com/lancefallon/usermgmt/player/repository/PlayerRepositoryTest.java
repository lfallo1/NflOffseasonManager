package com.lancefallon.usermgmt.player.repository;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import testhelpers.DomainFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class PlayerRepositoryTest {

    @InjectMocks
    PlayerRepository playerRepository;

    @Test
    void findAllThrowsException() {
        JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(RowMapper.class)));
        Mockito.when(playerRepository.getJdbcTemplate()).thenReturn(jdbcTemplate);

        assertThrows(DatabaseException.class, () -> this.playerRepository.findAll(null));
    }

    @Test
    void findAll() throws DatabaseException {
        JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        Mockito.doReturn(DomainFactory.createPlayers(10)).when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(RowMapper.class)));
        Mockito.when(playerRepository.getJdbcTemplate()).thenReturn(jdbcTemplate);

        List<Player> players = this.playerRepository.findAll(null);
        assertEquals(10, players.size());
    }

    @Test
    void findPlayerById() {
    }

    @Test
    void allPositions() {
    }

    @Test
    void allPositionCategories() {
    }

    @Test
    void allPositionSidesOfBall() {
    }

    @Test
    void allColleges() {
    }

    @Test
    void allConferences() {
    }

    @Test
    void allYears() {
    }

    @Test
    void updateNotes() {
    }

    @Test
    void addNotes() {
    }

    @Test
    void addPlayerNoteTag() {
    }

    @Test
    void getByUsername() {
    }

    @Test
    void deleteNotes() {
    }

    @Test
    void deleteNoteTags() {
    }

    @Test
    void getAllTags() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void updatePlayer() {
    }

    @Test
    void updateDraftInformation() {
    }

    @Test
    void getActiveImport() {
    }

    @Test
    void findLatestImport() {
    }
}
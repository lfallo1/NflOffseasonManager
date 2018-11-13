package com.lancefallon.usermgmt.player.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testhelpers.DomainFactory;

import static org.junit.jupiter.api.Assertions.*;

class PlayerModelTests {

    @Test
    @DisplayName("Test Player Model Properties")
    void testPlayerModelProps() {
        Conference conference = DomainFactory.generateConference(1, "TestConference");
        College college = DomainFactory.generateCollege(1, "TestCollege");
        college.setConf(conference);
        Player player = DomainFactory.createPlayers(1).get(0);

        assertAll(
                () -> assertAll("Testing Player Properties",
                        () -> assertTrue(player.getId().equals(1), "Player ID did not match"),
                        () -> assertTrue(player.getRank().equals(1), "Player rank did not match"),
                        () -> assertTrue(player.getName().equals("johndoe"), "Player name did not match")),

                () -> assertAll("Testing Conference Properties",
                        () -> assertTrue(conference.getId().equals(1), "Conference ID did not match"),
                        () -> assertEquals(conference.getName(), "TestConference", "Conference name did not match")),

                () -> assertAll("Testing College Properties",
                        () -> assertTrue(college.getId().equals(1), "College ID not match"),
                        () -> assertEquals(college.getName(), "TestCollege", "College name did not match")));
    }

}
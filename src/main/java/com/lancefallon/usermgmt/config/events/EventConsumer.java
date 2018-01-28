package com.lancefallon.usermgmt.config.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.player.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class EventConsumer {

    private Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * listen for messages related to import progress
     *
     * @param payload
     */
    @RabbitListener(queues = "parserProgressQueue")
    public void receive(String payload) {
        try {
            ParserProgressEvent message = new ObjectMapper().readValue(payload, ParserProgressEvent.class);
            playerRepository.addParserProgress(message);
        } catch (IOException | DatabaseException e) {
            logger.warn("Error adding parser progress: " + e.toString());
        }

    }

}
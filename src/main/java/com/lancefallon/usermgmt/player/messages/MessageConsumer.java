package com.lancefallon.usermgmt.player.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

public class MessageConsumer {

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    /**
     * listen for messages related to import progress
     *
     * @param payload
     */
    @RabbitListener(queues = "parserProgressQueue")
    public void receive(String payload) {
        try {
            ParserProgressMessage message = new ObjectMapper().readValue(payload, ParserProgressMessage.class);

            //broadcast to /topic/import channel
            messageTemplate.convertAndSend("/topic/import", message);

        } catch (IOException e) {
            logger.warn("Error adding parser progress: " + e.toString());
        }

    }

}
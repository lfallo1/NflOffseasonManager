package com.lancefallon.usermgmt.player.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lancefallon.usermgmt.player.model.DraftRefreshPayload;
import com.lancefallon.usermgmt.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.util.List;

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
                DraftRefreshPayload draftRefreshPayload = new ObjectMapper().readValue(payload, DraftRefreshPayload.class);

                //broadcast to /topic/import channel
                messageTemplate.convertAndSend("/topic/draft", draftRefreshPayload.getPlayers());
            } catch(IOException e){
                logger.info("Not of type DraftRefreshPayload");
            }

            try{
                ParserProgressMessage message = new ObjectMapper().readValue(payload, ParserProgressMessage.class);

                //broadcast to /topic/import channel
                messageTemplate.convertAndSend("/topic/import", message);
            } catch(IOException e){
                logger.info("Not of type ParserProgressMessage");
            }
    }

}
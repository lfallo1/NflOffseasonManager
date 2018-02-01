package com.lancefallon.usermgmt.player.messages;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConsumerConfiguration {

    @Bean
    @Qualifier("parserEventExchange")
    public Exchange messageExchange() {
        return new TopicExchange("parserEventExchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("parserProgressQueue");
    }

    @Bean
    public Binding binding(Queue queue, Exchange eventExchange) {
        return BindingBuilder
                .bind(queue)
                .to(eventExchange)
                .with("nflcombine.refresh.progress")
                .noargs();
    }

    @Bean
    public MessageConsumer messageConsumer() {
        return new MessageConsumer();
    }

}

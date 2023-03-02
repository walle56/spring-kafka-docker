package com.walle.skd.config;

import com.walle.skd.handler.ListenerForSkdTopic1;
import com.walle.skd.handler.MessageSender;
import com.walle.skd.handler.MessagesGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Application Spring beans configuration
 */
@Configuration
public class SkdBeansConfig {

    @Bean
    public MessageSender messageSender(KafkaTemplate<String, String> kafkaTemplate) {
        return new MessageSender(kafkaTemplate);
    }

    @Bean
    public ListenerForSkdTopic1 listenerForSkdTopic1() {
        return new ListenerForSkdTopic1();
    }

    @Bean
    public MessagesGenerator messagesGenerator(MessageSender messageSender) {
        return new MessagesGenerator(messageSender);
    }
}

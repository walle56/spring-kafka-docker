package com.walle.skd.config;

import com.walle.skd.handler.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class SkdBeansConfig {

    @Bean
    public MessageProducer messageSender(KafkaTemplate<String, String> kafkaTemplate) {
        return new MessageProducer(kafkaTemplate);
    }
}

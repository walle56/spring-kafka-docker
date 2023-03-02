package com.walle.skd.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(topics = "SKD-Topic1")
public class ListenerForSkdTopic1 {

    private static final Logger LOG = LoggerFactory.getLogger(ListenerForSkdTopic1.class);

    @KafkaHandler
    public void listenMessage(String message) {
        LOG.info("Received message: {}", message);
    }

    @KafkaHandler(isDefault = true)
    public void listenObject(Object obj) {
        LOG.info("Received object: {}", obj);
    }
    
}

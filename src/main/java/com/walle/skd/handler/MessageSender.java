package com.walle.skd.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Class for sending message to specific Kafka Topic
 */
public class MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String json) {
        LOG.debug("Sending message to the Topic {}, message body {}", topic, json);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, json);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Successfully sent message {} with offset={}", json, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.error("Failed to to send message {}", json, ex);
            }
        });
    }
}

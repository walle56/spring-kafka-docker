package com.walle.skd.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.walle.skd.model.Car;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * Class to generate messages every 10 seconds
 * It runs after Spring Boot server will be fully initialized
 */
public class MessagesGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(MessagesGenerator.class);
    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    private final MessageSender messageSender;

    public MessagesGenerator(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void generate() {

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {

            try {
                Car car = new Car("Superior", "TypeA", 2030, "Me", "AA" + new Random().nextInt(99));
                messageSender.send(topic, OBJECT_WRITER.writeValueAsString(car));
            } catch (Exception e) {
                LOG.error("Error while sending the message to Kafka", e);
            }

        }, 0, 10, TimeUnit.SECONDS);

    }

}

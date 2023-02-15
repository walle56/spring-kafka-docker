package com.walle.skd;

import com.walle.skd.handler.MessageProducer;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class to run the Spring Boot application
 */
@SpringBootApplication
public class SkdApp {

    @Autowired
    private MessageProducer messageProducer;

    public static void main(String[] args) {
        SpringApplication.run(SkdApp.class, args);
    }

    @PostConstruct
    public void test() {
        messageProducer.send("SKD-Topic1", "123");
    }
}

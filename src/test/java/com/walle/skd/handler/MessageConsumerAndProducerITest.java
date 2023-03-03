package com.walle.skd.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.walle.skd.model.Car;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Integration test of the Kafka message producer and consumer
 */
@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class MessageConsumerAndProducerITest {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();
    private static final String TOPIC = "SKD-Topic1";

    @MockBean
    private MessagesGenerator messagesGenerator;

    @Autowired
    private MessageSender messageSender;

    @SpyBean
    private ListenerForSkdTopic1 listener;

    @BeforeEach
    public void setup() {
        doNothing().when(messagesGenerator).generate();
    }

    @Test
    public void testProduceAndConsumeMessage() throws Exception {
        Car car = new Car("Superior", "Type A", 2030, "Me", "TT" + new Random().nextInt(99));

        messageSender.send(TOPIC, OBJECT_WRITER.writeValueAsString(car));
        Thread.sleep(500);

        verify(listener, times(1)).listenMessage(anyString());
    }

}

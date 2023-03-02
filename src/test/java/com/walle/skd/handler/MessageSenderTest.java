package com.walle.skd.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.walle.skd.model.Car;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageSenderTest {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessage() throws Exception {
        String carJson = getCarJson();
        String topic = "Topic1";

        messageSender.send(topic, carJson);

        verify(kafkaTemplate, times(1)).send(eq(topic), eq(carJson));
    }

    private String getCarJson() throws JsonProcessingException {
        Car car = new Car("Superior", "Type A", 2030, "Me", "AA11AA");
        return OBJECT_WRITER.writeValueAsString(car);
    }

}

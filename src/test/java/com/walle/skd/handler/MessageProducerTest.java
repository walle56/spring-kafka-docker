package com.walle.skd.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.walle.skd.model.Car;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageProducerTest {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MessageProducer messageProducer;

    public void testSendMessage() throws Exception {
        String carJson = getCarJson();
        String topic = "Topic1";

        messageProducer.send(topic, carJson);

        verify(kafkaTemplate, times(1)).send(eq(topic), eq(carJson));
    }

    private String getCarJson() throws JsonProcessingException {
        Car car = new Car("Superior", "Type A", 2030, "Me");
        return OBJECT_WRITER.writeValueAsString(car);
    }

}

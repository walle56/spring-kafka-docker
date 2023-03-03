package com.walle.skd.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.walle.skd.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageSenderUnitTest {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Test
    public void testSendMessage() throws Exception {
        String carJson = getCarJson();
        String topic = "Topic1";

        ListenableFuture futureMock = mock(ListenableFuture.class);
        when(kafkaTemplate.send(eq(topic), eq(carJson))).thenReturn(futureMock);

        messageSender.send(topic, carJson);

        verify(kafkaTemplate, times(1)).send(eq(topic), eq(carJson));
    }

    @Test
    public void testExceptionOnTopicNull() {
        when(kafkaTemplate.send(any(), any())).thenCallRealMethod();

        assertThrows(IllegalArgumentException.class, () -> {
            String carJson = getCarJson();
            messageSender.send(null, carJson);
        });
    }

    private String getCarJson() throws JsonProcessingException {
        Car car = new Car("Superior", "Type A", 2030, "Me", "AA11AA");
        return OBJECT_WRITER.writeValueAsString(car);
    }

}

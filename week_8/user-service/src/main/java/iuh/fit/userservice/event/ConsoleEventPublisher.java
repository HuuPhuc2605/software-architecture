package iuh.fit.userservice.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsoleEventPublisher implements EventPublisher {
    private static final Logger log = LoggerFactory.getLogger(ConsoleEventPublisher.class);

    @Override
    public void publishUserRegistered(Map<String, Object> payload) {
        // For now just log the event; can be replaced with Kafka/RabbitMQ publisher
        log.info("EVENT USER_REGISTERED: {}", payload);
    }
}

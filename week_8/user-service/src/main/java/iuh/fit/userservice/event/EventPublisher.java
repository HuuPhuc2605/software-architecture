package iuh.fit.userservice.event;

import java.util.Map;

public interface EventPublisher {
    void publishUserRegistered(Map<String, Object> payload);
}

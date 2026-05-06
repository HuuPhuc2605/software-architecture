package iuh.fit.payment_notificationservice.service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import iuh.fit.payment_notificationservice.events.BookingCreatedEvent;
import iuh.fit.payment_notificationservice.events.BookingFailedEvent;
import iuh.fit.payment_notificationservice.events.PaymentCompletedEvent;

@Component
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "BOOKING_CREATED", groupId = "payment-group")
    public void onBookingCreated(BookingCreatedEvent event) {
        System.out.println("[PAYMENT] Received booking: " + event.getBookingId());

        // simulate 80% success
        int r = ThreadLocalRandom.current().nextInt(100);
        if (r < 80) {
            PaymentCompletedEvent completed = new PaymentCompletedEvent();
            completed.setBookingId(event.getBookingId());
            completed.setUserId(event.getUserId());
            completed.setAmount(event.getAmount());
            completed.setStatus("SUCCESS");
            completed.setTimestamp(Instant.now().toString());
            completed.setSeats(event.getSeats());
            kafkaTemplate.send("PAYMENT_COMPLETED", completed);
            System.out.println("[PAYMENT] Published PAYMENT_COMPLETED for " + event.getBookingId());
        } else {
            BookingFailedEvent failed = new BookingFailedEvent();
            failed.setBookingId(event.getBookingId());
            failed.setUserId(event.getUserId());
            failed.setReason("Payment failed");
            failed.setTimestamp(Instant.now().toString());
            kafkaTemplate.send("BOOKING_FAILED", failed);
            System.out.println("[PAYMENT] Published BOOKING_FAILED for " + event.getBookingId());
        }
    }
}

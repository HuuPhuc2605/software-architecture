package iuh.fit.payment_notificationservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import iuh.fit.payment_notificationservice.events.BookingFailedEvent;
import iuh.fit.payment_notificationservice.events.PaymentCompletedEvent;

@Component
public class NotificationListener {

    @KafkaListener(topics = "PAYMENT_COMPLETED", groupId = "notification-group")
    public void onPaymentCompleted(@Payload PaymentCompletedEvent event) {
        System.out.println("[NOTIFICATION] User " + event.getUserId() + " đã đặt đơn #" + event.getBookingId() + " thành công!");
        System.out.println("[NOTIFICATION] Số ghế: " + event.getSeats() + " | Tổng tiền: " + formatCurrency(event.getAmount()));
    }

    @KafkaListener(topics = "BOOKING_FAILED", groupId = "notification-group")
    public void onBookingFailed(@Payload BookingFailedEvent event) {
        System.out.println("[NOTIFICATION] Đơn #" + event.getBookingId() + " thất bại. Vui lòng thử lại!");
    }

    private String formatCurrency(Long amount) {
        if (amount == null) return "0 VNĐ";
        return String.format("%,d VNĐ", amount);
    }
}

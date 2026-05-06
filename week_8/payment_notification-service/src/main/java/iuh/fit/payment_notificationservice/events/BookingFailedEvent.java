package iuh.fit.payment_notificationservice.events;

public class BookingFailedEvent {
    private String bookingId;
    private String userId;
    private String reason;
    private String timestamp;

    public BookingFailedEvent() {}

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

package iuh.fit.payment_notificationservice.events;

public class PaymentCompletedEvent {
    private String bookingId;
    private String userId;
    private Long amount;
    private String status;
    private String timestamp;
    private String seats;

    public PaymentCompletedEvent() {}

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getSeats() { return seats; }
    public void setSeats(String seats) { this.seats = seats; }
}

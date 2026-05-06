# 💳 Movie Ticket System – Payment & Notification Service

## Mô tả
Gồm 2 service chạy chung hoặc tách riêng:
- Payment Service: Lắng nghe BOOKING_CREATED, xử lý thanh toán (giả lập), publish kết quả
- Notification Service: Lắng nghe PAYMENT_COMPLETED, gửi thông báo

## Công nghệ
- Java 17 + Spring Boot 3.x
- Kafka / RabbitMQ (consume & publish event)
- (Tùy chọn) WebSocket hoặc log ra console cho Notification

## Luồng xử lý

BOOKING_CREATED
      ↓
[Payment Service]
  Random 80% success / 20% fail
      ↓                    ↓
PAYMENT_COMPLETED    BOOKING_FAILED
      ↓
[Notification Service]
  Log: "Booking #123 thành công!"

## Event Consuming & Publishing

### Payment Service
Consume topic: BOOKING_CREATED
Logic:
- Random success (80%) hoặc fail (20%)
- Nếu thành công → publish PAYMENT_COMPLETED
- Nếu thất bại   → publish BOOKING_FAILED

Payload PAYMENT_COMPLETED:
{
  "bookingId": "booking-001",
  "userId": "uuid-xxx",
  "amount": 170000,
  "status": "SUCCESS",
  "timestamp": "2024-01-01T10:05:30"
}

Payload BOOKING_FAILED:
{
  "bookingId": "booking-001",
  "userId": "uuid-xxx",
  "reason": "Payment failed",
  "timestamp": "2024-01-01T10:05:30"
}

### Notification Service
Consume topic: PAYMENT_COMPLETED
Output (log ra console):
[NOTIFICATION] User uuid-xxx đã đặt đơn #booking-001 thành công!
               Số ghế: A1, A2 | Tổng tiền: 170,000 VNĐ

Consume topic: BOOKING_FAILED  
Output:
[NOTIFICATION] Đơn #booking-001 thất bại. Vui lòng thử lại!

## Cấu hình (application.yml)
server:
  port: 8084

spring:
  kafka:
    bootstrap-servers: 192.168.?.?:9092
    consumer:
      group-id: payment-group

## Cài đặt & Chạy
./mvnw spring-boot:run

## Kịch bản Demo (BẮT BUỘC)
1. Chạy Kafka broker trên 192.168.?.?:9092
2. Đặt vé từ Frontend → Booking Service publish BOOKING_CREATED
3. Payment Service consume → random kết quả → publish event tiếp
4. Notification Service consume → log thông báo ra console
5. Booking Service nhận PAYMENT_COMPLETED → cập nhật status

## Checklist trước khi demo
- [ ] Kafka/RabbitMQ đã chạy
- [ ] Consumer group đã connect thành công
- [ ] Log hiển thị event được nhận
- [ ] Trạng thái booking thay đổi đúng (PENDING → CONFIRMED/FAILED)
- [ ] Notification log ra màn hình console

## IP triển khai
Payment + Notification: http://192.168.?.?:8084
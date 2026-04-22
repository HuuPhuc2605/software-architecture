# 👤 Movie Ticket System – User Service

## Mô tả
Service quản lý người dùng: đăng ký, đăng nhập và phát event khi có user mới.

## Công nghệ
- Java 17 + Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL / MySQL
- Kafka / RabbitMQ (publish event)

## API Endpoints
| Method | Endpoint    | Mô tả         |
|--------|-------------|---------------|
| POST   | /register   | Đăng ký       |
| POST   | /login      | Đăng nhập     |

## Request/Response mẫu

### POST /register
Request:
{
"username": "nguyen_van_a",
"email": "a@example.com",
"password": "123456"
}

Response:
{
"message": "Đăng ký thành công",
"userId": "uuid-xxx"
}

### POST /login
Request:
{
"email": "a@example.com",
"password": "123456"
}

Response:
{
"token": "eyJhbGci...",
"userId": "uuid-xxx"
}

## Event Publishing
Sau khi đăng ký thành công → publish event:

Topic: USER_REGISTERED
Payload:
{
"userId": "uuid-xxx",
"username": "nguyen_van_a",
"email": "a@example.com",
"timestamp": "2024-01-01T10:00:00"
}

## Cấu hình (application.yml)
server:
port: 8081

spring:
datasource:
url: jdbc:postgresql://localhost:5432/userdb
kafka:
bootstrap-servers: 192.168.?.?:9092

## Cài đặt & Chạy
./mvnw spring-boot:run

## IP triển khai
User Service: http://192.168.?.?:8081
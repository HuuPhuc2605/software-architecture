# ⚡ Flash Sale System — Space-Based Architecture

> **Buổi 7 — Kiến trúc và Thiết kế Phần mềm**  
> Hệ thống bán hàng flash sale chịu tải cao sử dụng Spring Boot + Redis (Data Grid)

---

## 📋 Mục lục

- [Tổng quan kiến trúc](#tổng-quan-kiến-trúc)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt & Khởi chạy](#cài-đặt--khởi-chạy)
- [Mô tả từng Processing Unit](#mô-tả-từng-processing-unit)
- [API Reference](#api-reference)
- [Luồng xử lý chính](#luồng-xử-lý-chính)
- [Kịch bản Test (Demo)](#kịch-bản-test-demo)
- [Mô hình triển khai LAN](#mô-hình-triển-khai-lan)
- [Bonus Features](#bonus-features)
- [Phân công nhóm](#phân-công-nhóm)

---

## 🏗️ Tổng quan kiến trúc

Dự án áp dụng **Space-Based Architecture** nhằm loại bỏ điểm nghẽn cổ chai (bottleneck) của database trong các tình huống tải cao.

```
┌─────────────────────────────────────────────────────────┐
│                     CLIENT LAYER                        │
│              ReactJS Frontend (:3000)                   │
└───────────────────────┬─────────────────────────────────┘
                        │ HTTP / REST API
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│   PU1:       │ │   PU2:       │ │   PU3:       │
│   Product    │ │   Cart       │ │   Order      │
│   (:8081)    │ │   (:8082)    │ │   (:8083)    │
└──────┬───────┘ └──────┬───────┘ └──────┬───────┘
       │                │                │
       └────────────────┼────────────────┘
                        │
               ┌────────▼────────┐
               │   DATA GRID     │
               │  Redis (:6379)  │
               │  (Shared RAM)   │
               └────────┬────────┘
                        │
               ┌────────▼────────┐
               │   PU4:          │
               │   Inventory     │
               │   (:8084)       │
               └─────────────────┘
```

### Nguyên lý Space-Based Architecture

| Nguyên lý                     | Cách áp dụng                                               |
| ----------------------------- | ---------------------------------------------------------- |
| **Không phụ thuộc DB**        | Mọi read/write đều qua Redis, không query DB trực tiếp     |
| **Data Grid (Shared Memory)** | Redis làm trung tâm lưu trữ dữ liệu dùng chung giữa các PU |
| **Processing Unit độc lập**   | Mỗi PU là một Spring Boot service riêng biệt               |
| **Low Latency**               | Thao tác trên RAM (Redis) thay vì disk (DB)                |

---

## 📁 Cấu trúc dự án

```
flash-sale-system/
├── README.md
├── docker-compose.yml              # Khởi chạy toàn bộ hệ thống
│
├── pu1-product-service/            # Processing Unit 1 - Product
│   ├── src/main/java/
│   │   └── com/flashsale/product/
│   │       ├── controller/
│   │       │   └── ProductController.java
│   │       ├── service/
│   │       │   └── ProductService.java
│   │       ├── model/
│   │       │   └── Product.java
│   │       └── config/
│   │           └── RedisConfig.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── pu2-cart-service/               # Processing Unit 2 - Cart
│   ├── src/main/java/
│   │   └── com/flashsale/cart/
│   │       ├── controller/
│   │       │   └── CartController.java
│   │       ├── service/
│   │       │   └── CartService.java
│   │       └── model/
│   │           └── CartItem.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── pu3-order-service/              # Processing Unit 3 - Order
│   ├── src/main/java/
│   │   └── com/flashsale/order/
│   │       ├── controller/
│   │       │   └── OrderController.java
│   │       ├── service/
│   │       │   └── OrderService.java
│   │       └── model/
│   │           └── Order.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── pu4-inventory-service/          # Processing Unit 4 - Inventory
│   ├── src/main/java/
│   │   └── com/flashsale/inventory/
│   │       ├── controller/
│   │       │   └── InventoryController.java
│   │       ├── service/
│   │       │   └── InventoryService.java
│   │       └── model/
│   │           └── StockItem.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
└── frontend/                       # ReactJS Frontend
    ├── src/
    │   ├── components/
    │   │   ├── ProductList.jsx
    │   │   ├── ProductDetail.jsx
    │   │   ├── Cart.jsx
    │   │   └── Checkout.jsx
    │   └── App.jsx
    └── package.json
```

---

## ⚙️ Yêu cầu hệ thống

| Công cụ | Phiên bản | Ghi chú              |
| ------- | --------- | -------------------- |
| Java    | 17+       | Spring Boot 3.x      |
| Maven   | 3.8+      | Build tool           |
| Redis   | 7.x       | Data Grid            |
| Docker  | 20.x+     | (Khuyến nghị)        |
| Node.js | 18+       | Cho ReactJS frontend |

---

## 🚀 Cài đặt & Khởi chạy

### Cách 1: Dùng Docker Compose (Khuyến nghị)

```bash
# Clone dự án
git clone https://github.com/<your-team>/flash-sale-system.git
cd flash-sale-system

# Khởi chạy toàn bộ hệ thống (Redis + tất cả PU)
docker-compose up -d

# Kiểm tra logs
docker-compose logs -f
```

### Cách 2: Chạy thủ công từng service

**Bước 1: Khởi động Redis**

```bash
# Dùng Docker
docker run -d --name redis -p 6379:6379 redis:7

# Hoặc cài đặt Redis native (Ubuntu)
sudo apt-get install redis-server
sudo service redis-server start
```

**Bước 2: Seed dữ liệu vào Redis**

```bash
redis-cli

# Thêm sản phẩm mẫu
HSET product:1 id 1 name "iPhone 15 Pro" price 25000000 stock 100
HSET product:2 id 2 name "Samsung S24" price 20000000 stock 50
HSET product:3 id 3 name "MacBook Air M3" price 35000000 stock 30

# Khởi tạo tồn kho
SET stock:1 100
SET stock:2 50
SET stock:3 30
```

**Bước 3: Khởi động từng Processing Unit**

```bash
# PU1 - Product Service (port 8081)
cd pu1-product-service
mvn spring-boot:run

# PU2 - Cart Service (port 8082)
cd pu2-cart-service
mvn spring-boot:run

# PU3 - Order Service (port 8083)
cd pu3-order-service
mvn spring-boot:run

# PU4 - Inventory Service (port 8084)
cd pu4-inventory-service
mvn spring-boot:run
```

**Bước 4: Khởi động Frontend**

```bash
cd frontend
npm install
npm start
# Truy cập: http://localhost:3000
```

---

## 📦 Mô tả từng Processing Unit

### PU1 — Product Service (`:8081`)

**Trách nhiệm:** Cung cấp thông tin sản phẩm từ Data Grid (Redis).

**Cấu hình `application.yml`:**

```yaml
server:
  port: 8081
spring:
  application:
    name: pu1-product-service
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: 6379
```

**Dependencies (`pom.xml`):**

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

**Ví dụ Service:**

```java
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RedisTemplate<String, Object> redisTemplate;

    public List<Product> getAllProducts() {
        // Lấy dữ liệu từ Redis, KHÔNG query DB
        Set<String> keys = redisTemplate.keys("product:*");
        return keys.stream()
            .map(key -> (Product) redisTemplate.opsForValue().get(key))
            .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return (Product) redisTemplate.opsForValue().get("product:" + id);
    }
}
```

---

### PU2 — Cart Service (`:8082`)

**Trách nhiệm:** Quản lý giỏ hàng của user, lưu trữ trong Redis theo session.

**Key pattern trong Redis:** `cart:{userId}` → List of CartItem (JSON)

**Ví dụ Service:**

```java
@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void addToCart(String userId, CartItem item) {
        String key = "cart:" + userId;
        // Lưu cart item vào Redis List
        redisTemplate.opsForList().rightPush(key, item);
        redisTemplate.expire(key, Duration.ofHours(2)); // TTL 2 giờ
    }

    public List<CartItem> getCart(String userId) {
        String key = "cart:" + userId;
        return (List<CartItem>) redisTemplate.opsForList().range(key, 0, -1);
    }
}
```

---

### PU3 — Order Service (`:8083`)

**Trách nhiệm:** Xử lý checkout — lấy giỏ hàng từ Redis, tạo đơn hàng, trigger giảm tồn kho.

**Ví dụ Service:**

```java
@Service
@RequiredArgsConstructor
public class OrderService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;

    @Transactional
    public OrderResult checkout(String userId) {
        // 1. Lấy cart từ Data Grid
        List<CartItem> cartItems = getCartFromRedis(userId);

        // 2. Gọi Inventory PU giảm stock
        for (CartItem item : cartItems) {
            restTemplate.postForObject(
                "http://localhost:8084/stock/decrease",
                new StockRequest(item.getProductId(), item.getQuantity()),
                Void.class
            );
        }

        // 3. Tạo order ID và lưu vào Redis
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, cartItems, LocalDateTime.now());
        redisTemplate.opsForValue().set("order:" + orderId, order, Duration.ofDays(1));

        // 4. Xóa cart sau khi đặt hàng thành công
        redisTemplate.delete("cart:" + userId);

        return new OrderResult(orderId, "SUCCESS");
    }
}
```

---

### PU4 — Inventory Service (`:8084`)

**Trách nhiệm:** Quản lý tồn kho trực tiếp trên Redis, **không đọc/ghi DB**.

**Key pattern trong Redis:** `stock:{productId}` → Integer (số lượng tồn)

**Ví dụ Service (với Atomic Operation):**

```java
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final RedisTemplate<String, Object> redisTemplate;

    public Integer getStock(Long productId) {
        String key = "stock:" + productId;
        Object val = redisTemplate.opsForValue().get(key);
        return val != null ? Integer.parseInt(val.toString()) : 0;
    }

    public boolean decreaseStock(Long productId, int quantity) {
        String key = "stock:" + productId;
        // Dùng DECRBY — atomic operation, thread-safe
        Long remaining = redisTemplate.opsForValue().decrement(key, quantity);
        if (remaining < 0) {
            // Rollback nếu stock âm
            redisTemplate.opsForValue().increment(key, quantity);
            return false; // Hết hàng
        }
        return true;
    }
}
```

---

## 📡 API Reference

### PU1 — Product API

| Method | Endpoint         | Mô tả                  | Response        |
| ------ | ---------------- | ---------------------- | --------------- |
| GET    | `/products`      | Lấy danh sách sản phẩm | `List<Product>` |
| GET    | `/products/{id}` | Lấy chi tiết sản phẩm  | `Product`       |

**Ví dụ response `/products`:**

```json
[
  {
    "id": 1,
    "name": "iPhone 15 Pro",
    "price": 25000000,
    "description": "Apple iPhone 15 Pro 256GB",
    "stock": 100
  }
]
```

---

### PU2 — Cart API

| Method | Endpoint         | Mô tả                 | Body                              |
| ------ | ---------------- | --------------------- | --------------------------------- |
| POST   | `/cart/add`      | Thêm sản phẩm vào giỏ | `{ userId, productId, quantity }` |
| GET    | `/cart/{userId}` | Xem giỏ hàng          | —                                 |
| DELETE | `/cart/{userId}` | Xóa giỏ hàng          | —                                 |

**Ví dụ request `POST /cart/add`:**

```json
{
  "userId": "user-123",
  "productId": 1,
  "quantity": 2
}
```

---

### PU3 — Order API

| Method | Endpoint            | Mô tả               | Body         |
| ------ | ------------------- | ------------------- | ------------ |
| POST   | `/checkout`         | Đặt hàng (checkout) | `{ userId }` |
| GET    | `/orders/{orderId}` | Xem đơn hàng        | —            |

**Ví dụ response `POST /checkout`:**

```json
{
  "orderId": "a1b2c3d4-...",
  "status": "SUCCESS",
  "message": "Đặt hàng thành công",
  "timestamp": "2025-01-01T10:00:00"
}
```

---

### PU4 — Inventory API

| Method | Endpoint             | Mô tả        | Body                      |
| ------ | -------------------- | ------------ | ------------------------- |
| GET    | `/stock/{productId}` | Xem tồn kho  | —                         |
| POST   | `/stock/decrease`    | Giảm tồn kho | `{ productId, quantity }` |

**Ví dụ response `GET /stock/1`:**

```json
{
  "productId": 1,
  "productName": "iPhone 15 Pro",
  "stock": 98
}
```

---

## 🔄 Luồng xử lý chính

### Luồng đặt hàng (Checkout Flow)

```
User                 Frontend          PU2-Cart         PU3-Order        PU4-Inventory       Redis
 │                      │                 │                │                  │                │
 │── Chọn SP ──────────►│                 │                │                  │                │
 │                      │──POST /cart/add►│                │                  │                │
 │                      │                 │────────────────────────────────────────── LPUSH cart:{uid} ──►│
 │                      │◄── 200 OK ──────│                │                  │                │
 │── Checkout ─────────►│                 │                │                  │                │
 │                      │──POST /checkout──────────────────►│                  │                │
 │                      │                 │                │──── GET cart:{uid} ───────────────►│
 │                      │                 │                │◄─── CartItems ─────────────────────│
 │                      │                 │                │──POST /stock/decrease ────────────►│
 │                      │                 │                │                  │── DECRBY stock ►│
 │                      │                 │                │◄── true/false ───│                │
 │                      │                 │                │──── SET order:{id} ───────────────►│
 │                      │                 │                │──── DEL cart:{uid} ───────────────►│
 │                      │◄────────────────────── orderId + SUCCESS ──────────│                │
 │◄── Kết quả ──────────│                 │                │                  │                │
```

### Nguyên tắc quan trọng

- **Không có bước nào đọc/ghi Database** — toàn bộ xử lý trên Redis (RAM)
- Stock giảm ngay lập tức bằng lệnh `DECRBY` (atomic, thread-safe)
- Kết quả trả về ngay mà không cần chờ DB commit

---

## 🧪 Kịch bản Test (Demo)

### 1. Test Load danh sách sản phẩm từ Redis

```bash
curl http://localhost:8081/products
```

Kết quả mong đợi: danh sách sản phẩm trả về từ Redis (không có DB query).

---

### 2. Test Add to Cart

```bash
curl -X POST http://localhost:8082/cart/add \
  -H "Content-Type: application/json" \
  -d '{"userId": "user-001", "productId": 1, "quantity": 2}'
```

Verify trong Redis:

```bash
redis-cli LRANGE cart:user-001 0 -1
```

---

### 3. Test Checkout

```bash
curl -X POST http://localhost:8083/checkout \
  -H "Content-Type: application/json" \
  -d '{"userId": "user-001"}'
```

---

### 4. Verify Stock giảm ngay lập tức

```bash
# Trước checkout
redis-cli GET stock:1
# → 100

# Sau checkout (đặt 2 cái)
redis-cli GET stock:1
# → 98

# Hoặc qua API
curl http://localhost:8084/stock/1
```

---

### 5. Load Test — Không bị chậm khi nhiều request

**Dùng curl đơn giản:**

```bash
# Gửi 100 request đồng thời
for i in $(seq 1 100); do
  curl -s -X POST http://localhost:8082/cart/add \
    -H "Content-Type: application/json" \
    -d "{\"userId\": \"user-$i\", \"productId\": 1, \"quantity\": 1}" &
done
wait
echo "Done!"
```

**Dùng Apache Bench:**

```bash
ab -n 1000 -c 100 http://localhost:8081/products
```

**Dùng Postman Runner:**

- Import collection → Set iterations = 500
- Concurrent users = 50
- Quan sát response time (mục tiêu < 50ms)

---

## 🌐 Mô hình triển khai LAN

Thay địa chỉ IP phù hợp với máy của từng thành viên trong nhóm:

| Service           | Máy chủ                   | Port | IP Ví dụ             |
| ----------------- | ------------------------- | ---- | -------------------- |
| Redis (Data Grid) | Người 2 hoặc server chung | 6379 | `192.168.1.100:6379` |
| PU1 — Product     | Người 2                   | 8081 | `192.168.1.101:8081` |
| PU2 — Cart        | Người 3                   | 8082 | `192.168.1.102:8082` |
| PU3 — Order       | Người 4                   | 8083 | `192.168.1.103:8083` |
| PU4 — Inventory   | Người 5                   | 8084 | `192.168.1.104:8084` |
| Frontend (React)  | Người 1                   | 3000 | `192.168.1.105:3000` |

**Cấu hình Redis remote trong `application.yml`:**

```yaml
spring:
  data:
    redis:
      host: 192.168.1.100 # IP máy chạy Redis
      port: 6379
```

**Lưu ý:** Đảm bảo tường lửa (firewall) mở các port cần thiết giữa các máy trong LAN.

---

## ⭐ Bonus Features

### 1. Dùng Hazelcast thay Redis

Thêm dependency:

```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-spring</artifactId>
</dependency>
```

### 2. Implement Distributed Locking (SETNX)

Tránh oversell khi nhiều user cùng checkout một sản phẩm:

```java
public boolean tryLock(String productId) {
    Boolean acquired = redisTemplate.opsForValue()
        .setIfAbsent("lock:stock:" + productId, "1", Duration.ofSeconds(5));
    return Boolean.TRUE.equals(acquired);
}

public void unlock(String productId) {
    redisTemplate.delete("lock:stock:" + productId);
}
```

### 3. Async Queue với Redis List

```java
// Publisher (Order PU)
redisTemplate.opsForList().leftPush("order:queue", orderJson);

// Consumer (Background worker)
@Scheduled(fixedDelay = 100)
public void processQueue() {
    String orderJson = (String) redisTemplate.opsForList().rightPop("order:queue");
    if (orderJson != null) {
        // Xử lý order bất đồng bộ
    }
}
```

### 4. Simulate Load Test với Postman Runner

- Tạo Postman Collection với các request: Get Products → Add Cart → Checkout
- Chạy với 200+ iterations, 50 concurrent users
- Export kết quả và so sánh response time

---

## 👥 Phân công nhóm

| Người       | Vai trò                 | Nhiệm vụ                                                     |
| ----------- | ----------------------- | ------------------------------------------------------------ |
| **Người 1** | Frontend (ReactJS)      | UI danh sách SP, giỏ hàng, đặt hàng; Gọi API vào các PU      |
| **Người 2** | PU1 — Product Service   | `GET /products`, `GET /products/{id}`; Load từ Redis         |
| **Người 3** | PU2 — Cart Service      | `POST /cart/add`, `GET /cart/{userId}`; Lưu cart trong Redis |
| **Người 4** | PU3 — Order Service     | `POST /checkout`; Lấy cart → tạo order → trigger inventory   |
| **Người 5** | PU4 — Inventory Service | `GET /stock/{productId}`; Giảm tồn kho atomic trên Redis     |

---

## 📊 Tiêu chí chấm điểm

| Tiêu chí                      | Yêu cầu                                   | Điểm     |
| ----------------------------- | ----------------------------------------- | -------- |
| Đúng Space-Based Architecture | Các PU độc lập, đúng vai trò              | 3.0      |
| Không phụ thuộc DB            | Mọi thao tác qua Redis, không query DB    | 2.5      |
| Dùng Data Grid đúng           | Redis làm shared memory, đúng key pattern | 2.0      |
| Flow nhanh, không nghẽn       | Response time thấp dưới tải               | 1.5      |
| Demo scale (clone PU)         | Chạy 2 instance PU1 cùng lúc              | 1.0      |
| **Tổng**                      |                                           | **10.0** |

---

## 🛠️ Troubleshooting

**Lỗi kết nối Redis:**

```
RedisConnectionFailureException: Unable to connect to localhost:6379
```

→ Kiểm tra Redis đang chạy: `redis-cli ping` (expected: `PONG`)

**Lỗi CORS khi Frontend gọi API:**

```java
// Thêm vào mỗi Controller
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController { ... }
```

**Stock bị âm (race condition):**
→ Implement distributed lock với `SETNX` (xem phần Bonus)

---

## 📚 Tài liệu tham khảo

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data Redis](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)
- [Redis Commands Reference](https://redis.io/commands/)
- [Space-Based Architecture Pattern](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch05.html)

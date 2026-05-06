package iuh.fit.userservice.controller;

import iuh.fit.userservice.dto.AuthResponse;
import iuh.fit.userservice.dto.LoginRequest;
import iuh.fit.userservice.dto.RegisterRequest;
import iuh.fit.userservice.event.EventPublisher;
import iuh.fit.userservice.model.User;
import iuh.fit.userservice.repository.UserRepository;
// JWT removed: simple login response used
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public AuthController(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/")
    public ResponseEntity<?> info() {
        return ResponseEntity.ok(Map.of("message", "User Service: use POST /register and POST /login"));
    }

    @GetMapping("/register")
    public ResponseEntity<?> registerInfo() {
        return ResponseEntity.status(405).body(Map.of("message", "Use POST /register with username,email,password"));
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginInfo() {
        return ResponseEntity.status(405).body(Map.of("message", "Use POST /login with email,password"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        String email = req.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email đã tồn tại"));
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(email);
        // store plaintext password as requested
        user.setPassword(req.getPassword());
        userRepository.save(user);

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId().toString());
        payload.put("username", user.getUsername());
        payload.put("email", user.getEmail());
        payload.put("timestamp", java.time.Instant.now().toString());
        eventPublisher.publishUserRegistered(payload);

        return ResponseEntity.ok(Map.of("message", "Đăng ký thành công", "userId", user.getId().toString()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String email = req.getEmail().trim().toLowerCase();
        var opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        User user = opt.get();
        String stored = user.getPassword();
        if (stored == null || !stored.equals(req.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        return ResponseEntity.ok(new AuthResponse("Đăng nhập thành công", user.getId().toString()));
    }
}

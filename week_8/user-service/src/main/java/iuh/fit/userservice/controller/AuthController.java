package iuh.fit.userservice.controller;

import iuh.fit.userservice.dto.AuthResponse;
import iuh.fit.userservice.dto.LoginRequest;
import iuh.fit.userservice.dto.RegisterRequest;
import iuh.fit.userservice.event.EventPublisher;
import iuh.fit.userservice.model.User;
import iuh.fit.userservice.repository.UserRepository;
import iuh.fit.userservice.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EventPublisher eventPublisher;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email đã tồn tại"));
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
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
        var opt = userRepository.findByEmail(req.getEmail());
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        User user = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getId().toString(), user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, user.getId().toString()));
    }
}

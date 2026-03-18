package com.example.servicea.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServiceA {

    @CircuitBreaker(name = "serviceB", fallbackMethod = "fallback")
    public String callServiceB() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Service B is down");
        }
        return "Response from Service B";
    }

    public String fallback(Throwable ex) {
        return "Fallback: Service B đang lỗi!";
    }
}

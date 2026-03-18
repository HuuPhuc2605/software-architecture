package com.example.serviceb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b")
public class ServiceBController {

    @GetMapping("/hello")
    public String hello() {
        throw new RuntimeException("Service B crashed");
    }
}

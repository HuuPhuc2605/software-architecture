package com.example.servicea.controller;

import com.example.servicea.service.ServiceA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ServiceA serviceA;
//    private final RateLimitService  rateLimitService;
//    private final BulkheadService bulkheadService;

    public TestController(ServiceA serviceA){
//                          RateLimitService rateLimitService,
//                          BulkheadService bulkheadService) {
        this.serviceA = serviceA;
//        this.rateLimitService = rateLimitService;
//        this.bulkheadService = bulkheadService;
    }

    @GetMapping("/cb")
    public String testCB() {
        return serviceA.callServiceB();
    }

//    @GetMapping("/rate")
//    public String testRate() {
//        return rateLimitService.accessApi();
//    }
//
//    @GetMapping("/bulkhead")
//    public String testBulkhead() throws InterruptedException {
//        return bulkheadService.process();
//    }
}

package com.tddweekly.java.example.support;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans")
class SupportController {
    private final SupportService service;

    SupportController(SupportService service) {
        this.service = service;
    }

    @GetMapping
    List<SupportPlan> readAll() {
        return service.getAvailableSupportPlans();
    }
}

package com.tddweekly.java.example.support;

import com.tddweekly.java.example.organization.OrganizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SupportConfiguration {
    @Bean
    SupportService supportFacade(OrganizationService organizationService) {
        return new SupportService(organizationService, new InMemorySupportPlanRepository());
    }
}

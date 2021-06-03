package com.tddweekly.java.example.organization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class OrganizationConfiguration {
    @Bean
    @RequestScope
    OrganizationServiceImpl organizationService() {
        return new OrganizationServiceImpl();
    }
}

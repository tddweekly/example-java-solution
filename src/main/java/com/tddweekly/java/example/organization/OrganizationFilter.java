package com.tddweekly.java.example.organization;

import com.tddweekly.java.example.organization.Organization.Tier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
class OrganizationFilter extends OncePerRequestFilter {
    static final String TIER_HEADER = "x-organization-tier";
    private static final Organization DEFAULT_ORGANIZATION = new Organization(Tier.FREE);

    private final OrganizationServiceImpl organizationService;

    OrganizationFilter(OrganizationServiceImpl organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        organizationService.setOrganization(
                Optional.ofNullable(request.getHeader(TIER_HEADER))
                        .map(this::createOrganizationFrom)
                        .orElse(DEFAULT_ORGANIZATION)
        );
        filterChain.doFilter(request, response);
    }

    private Organization createOrganizationFrom(String tier) {
        try {
            return new Organization(Tier.valueOf(tier.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return DEFAULT_ORGANIZATION;
        }
    }
}

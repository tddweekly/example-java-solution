package com.tddweekly.java.example.support;

import com.tddweekly.java.example.organization.OrganizationService;

import java.util.List;

import static com.tddweekly.java.example.organization.Organization.Tier.FREE;
import static com.tddweekly.java.example.support.SupportPlan.Type.BASIC;

class SupportService {
    private final OrganizationService organizationService;
    private final InMemorySupportPlanRepository repository;

    SupportService(OrganizationService organizationService, InMemorySupportPlanRepository repository) {
        this.organizationService = organizationService;
        this.repository = repository;
    }

    public List<SupportPlan> getAvailableSupportPlans() {
        return repository.findAll().stream()
                .filter(this::skipFreePlansForFullTiers)
                .map(plan -> isUnsupportedByOrganizationTier(plan) ? plan.disable() : plan)
                .toList();
    }

    private boolean skipFreePlansForFullTiers(SupportPlan plan) {
        return isOrganizationLimited() || plan.type() != BASIC;
    }

    private boolean isUnsupportedByOrganizationTier(SupportPlan plan) {
        return isOrganizationLimited() && plan.type() != BASIC;
    }

    private boolean isOrganizationLimited() {
        return organizationService.current().tier() == FREE;
    }
}

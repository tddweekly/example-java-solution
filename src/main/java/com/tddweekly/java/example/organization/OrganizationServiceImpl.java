package com.tddweekly.java.example.organization;

class OrganizationServiceImpl implements OrganizationService {
    private Organization org;

    @Override
    public Organization current() {
        return org;
    }

    void setOrganization(Organization org) {
        this.org = org;
    }
}

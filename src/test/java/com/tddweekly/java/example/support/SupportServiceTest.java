package com.tddweekly.java.example.support;

import com.tddweekly.java.example.organization.Organization;
import com.tddweekly.java.example.organization.Organization.Tier;
import com.tddweekly.java.example.organization.OrganizationService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static com.tddweekly.java.example.support.SupportPlan.Type.BASIC;
import static com.tddweekly.java.example.support.SupportPlan.Type.DEDICATED;
import static com.tddweekly.java.example.support.SupportPlan.Type.FULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class SupportServiceTest {
    @ParameterizedTest(name = "{0}")
    @EnumSource(value = Tier.class, names = {"PREMIUM", "PREMIUM_PLUS"})
    void testFullTier_getAvailableSupportPlans_filtersBasicPlan(Tier tier) {
        // given
        SupportService toTest = new SupportConfiguration()
                .supportFacade(organizationReturning(tier));

        // when
        List<SupportPlan> result = toTest.getAvailableSupportPlans();

        // then
        assertThat(result).extracting("type").containsExactlyInAnyOrder(FULL, DEDICATED);
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(value = Tier.class, names = {"FREE"})
    void testLimitedTier_getAvailableSupportPlans_disablesPremiumPlans(Tier tier) {
        // given
        SupportService toTest = new SupportConfiguration()
                .supportFacade(organizationReturning(tier));

        // when
        List<SupportPlan> result = toTest.getAvailableSupportPlans();

        // then
        assertThat(result)
                .extracting("type", "disabled")
                .containsExactlyInAnyOrder(
                        tuple(BASIC, false),
                        tuple(FULL, true),
                        tuple(DEDICATED, true));
    }

    private static OrganizationService organizationReturning(Tier tier) {
        return () -> new Organization(tier);
    }
}
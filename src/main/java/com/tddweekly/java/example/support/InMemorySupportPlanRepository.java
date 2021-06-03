package com.tddweekly.java.example.support;

import java.util.List;

class InMemorySupportPlanRepository {
    List<SupportPlan> findAll() {
        return List.of(
                SupportPlan.of(SupportPlan.Type.BASIC),
                SupportPlan.of(SupportPlan.Type.FULL),
                SupportPlan.of(SupportPlan.Type.DEDICATED)
        );
    }
}

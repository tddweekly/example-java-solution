package com.tddweekly.java.example.support;

import com.fasterxml.jackson.annotation.JsonProperty;

record SupportPlan(Type type, boolean disabled) {
    static SupportPlan of(Type type) {
        return new SupportPlan(type, false);
    }

    @JsonProperty
    String description() {
        return type.description;
    }

    SupportPlan disable() {
        return new SupportPlan(type, true);
    }

    enum Type {
        BASIC("Chat and e-mail support during working hours"),
        FULL("All support channels, 24/7"),
        DEDICATED("All support channels, 24/7 and a dedicated contact person");

        final String description;

        Type(String description) {
            this.description = description;
        }
    }
}

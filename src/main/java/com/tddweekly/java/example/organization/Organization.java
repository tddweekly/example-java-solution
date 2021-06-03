package com.tddweekly.java.example.organization;

public record Organization(Tier tier) {
    public enum Tier {
        FREE, PREMIUM, PREMIUM_PLUS
    }
}

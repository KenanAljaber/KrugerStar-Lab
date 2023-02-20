package com.krugerstarlab.entity.tutor;

public enum TutorType {
    BACKEND("Backend"),
    FRONTEND("Frontend"),
    FULL_STACK("Full Stack"),
    DATABASES("Databases"),
    NETWORKS("Networks");

    private String description;

    TutorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
package com.temat25zad1;

public enum TaskStatus {
    DONE("Ukończone", "green"),
    IN_PROGRESS("W trackie", "orange"),
    NOT_DONE("Do zrobienia", "red");

    private final String description;
    private final String statusColor;

    TaskStatus(String description, String statusColor) {
        this.description = description;
        this.statusColor = statusColor;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public String getDescription() {
        return description;
    }
}

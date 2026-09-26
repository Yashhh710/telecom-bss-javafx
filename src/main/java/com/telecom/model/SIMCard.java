package com.telecom.model;

import java.time.LocalDate;

public class SIMCard {
    private String simNumber;
    private boolean active;
    private LocalDate activationDate;

    public SIMCard(String simNumber) {
        this.simNumber = simNumber;
        this.active = false;
    }

    public String getSimNumber() { return simNumber; }
    public boolean isActive() { return active; }
    public LocalDate getActivationDate() { return activationDate; }

    public void activate() {
        active = true;
        activationDate = LocalDate.now();
    }

    public void deactivate() {
        active = false;
    }
}

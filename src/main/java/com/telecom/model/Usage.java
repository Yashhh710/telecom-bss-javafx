package com.telecom.model;

import java.time.LocalDate;

public class Usage {
    private String simNumber;
    private LocalDate date;
    private int callMinutes;
    private int smsCount;
    private double dataUsed;

    public Usage(String simNumber, LocalDate date, int callMinutes, int smsCount, double dataUsed) {
        this.simNumber = simNumber;
        this.date = date;
        this.callMinutes = callMinutes;
        this.smsCount = smsCount;
        this.dataUsed = dataUsed;
    }

    public String getSimNumber() { return simNumber; }
    public LocalDate getDate() { return date; }
    public int getCallMinutes() { return callMinutes; }
    public int getSmsCount() { return smsCount; }
    public double getDataUsed() { return dataUsed; }
}

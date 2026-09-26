package com.telecom.model;

public class Plan {
    private String planCode;
    private String planName;
    private PlanType planType;
    private double monthlyRent;
    private double dataLimit;
    private int callLimit;
    private int smsLimit;

    public Plan(String planCode, String planName, PlanType planType, double monthlyRent,
                double dataLimit, int callLimit, int smsLimit) {
        this.planCode = planCode;
        this.planName = planName;
        this.planType = planType;
        this.monthlyRent = monthlyRent;
        this.dataLimit = dataLimit;
        this.callLimit = callLimit;
        this.smsLimit = smsLimit;
    }

    public String getPlanCode() { return planCode; }
    public String getPlanName() { return planName; }
    public PlanType getPlanType() { return planType; }
    public double getMonthlyRent() { return monthlyRent; }
    public double getDataLimit() { return dataLimit; }
    public int getCallLimit() { return callLimit; }
    public int getSmsLimit() { return smsLimit; }

    @Override
    public String toString() {
        return planCode + " - " + planName + " (₹" + String.format("%.2f", monthlyRent) + ")";
    }
}

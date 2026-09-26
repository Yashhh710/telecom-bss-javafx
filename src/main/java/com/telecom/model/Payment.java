package com.telecom.model;

import java.time.LocalDateTime;

public class Payment {
    private String paymentId;
    private String billId;
    private double amount;
    private String method;
    private LocalDateTime paymentDate;

    public Payment(String paymentId, String billId, double amount, String method) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.amount = amount;
        this.method = method;
        this.paymentDate = LocalDateTime.now();
    }

    public String getPaymentId() { return paymentId; }
    public String getBillId() { return billId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
}

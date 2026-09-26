package com.telecom.model;

import java.time.LocalDate;

public class Bill {
    private String billId;
    private int customerId;
    private String month;
    private LocalDate billDate;
    private double baseAmount;
    private double callCharges;
    private double smsCharges;
    private double dataCharges;
    private double totalAmount;
    private PaymentStatus paymentStatus;

    public Bill(String billId, int customerId, String month, LocalDate billDate,
                double baseAmount, double callCharges, double smsCharges, double dataCharges) {
        this.billId = billId;
        this.customerId = customerId;
        this.month = month;
        this.billDate = billDate;
        this.baseAmount = baseAmount;
        this.callCharges = callCharges;
        this.smsCharges = smsCharges;
        this.dataCharges = dataCharges;
        this.totalAmount = baseAmount + callCharges + smsCharges + dataCharges;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public String getBillId() { return billId; }
    public int getCustomerId() { return customerId; }
    public String getMonth() { return month; }
    public LocalDate getBillDate() { return billDate; }
    public double getBaseAmount() { return baseAmount; }
    public double getCallCharges() { return callCharges; }
    public double getSmsCharges() { return smsCharges; }
    public double getDataCharges() { return dataCharges; }
    public double getTotalAmount() { return totalAmount; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus status) { paymentStatus = status; }
}

package com.telecom.util;

import com.telecom.model.Bill;
import com.telecom.model.Customer;

public final class BillGenerator {
    private BillGenerator() {}

    public static String generate(Customer customer, Bill bill) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("          TELECOM INVOICE\n");
        sb.append("========================================\n");
        sb.append("Bill ID       : ").append(bill.getBillId()).append("\n");
        sb.append("Customer      : ").append(customer.getName()).append("\n");
        sb.append("Phone         : ").append(customer.getPhone()).append("\n");
        sb.append("SIM           : ").append(customer.getSimCard() == null ? "-" : customer.getSimCard().getSimNumber()).append("\n");
        sb.append("Plan          : ").append(customer.getPlan() == null ? "-" : customer.getPlan().getPlanName()).append("\n");
        sb.append("Billing Month : ").append(bill.getMonth()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append(String.format("Base Amount   : ₹%.2f%n", bill.getBaseAmount()));
        sb.append(String.format("Call Charges  : ₹%.2f%n", bill.getCallCharges()));
        sb.append(String.format("SMS Charges   : ₹%.2f%n", bill.getSmsCharges()));
        sb.append(String.format("Data Charges  : ₹%.2f%n", bill.getDataCharges()));
        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL         : ₹%.2f%n", bill.getTotalAmount()));
        sb.append("Status        : ").append(bill.getPaymentStatus()).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}

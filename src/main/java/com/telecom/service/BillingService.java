package com.telecom.service;

import com.telecom.model.*;
import com.telecom.repository.DataStore;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BillingService {
    public Bill generate(Customer customer, String month) {
        Plan plan = customer.getPlan();
        double base = plan == null ? 0 : plan.getMonthlyRent();
        String sim = customer.getSimCard() == null ? "" : customer.getSimCard().getSimNumber();

        List<Usage> usage = DataStore.usageHistory.stream()
                .filter(u -> u.getSimNumber().equals(sim))
                .toList();

        int calls = usage.stream().mapToInt(Usage::getCallMinutes).sum();
        int sms = usage.stream().mapToInt(Usage::getSmsCount).sum();
        double data = usage.stream().mapToDouble(Usage::getDataUsed).sum();

        double callCharges = Math.max(0, calls - (plan == null ? 0 : plan.getCallLimit())) * 0.50;
        double smsCharges = Math.max(0, sms - (plan == null ? 0 : plan.getSmsLimit())) * 0.20;
        double dataCharges = Math.max(0, data - (plan == null ? 0 : plan.getDataLimit())) * 10.0;

        String id = "BILL-" + System.currentTimeMillis();
        Bill bill = new Bill(id, customer.getCustomerId(), month, LocalDate.now(),
                base, callCharges, smsCharges, dataCharges);
        DataStore.bills.put(LocalDate.now(), bill);
        return bill;
    }
}

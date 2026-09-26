package com.telecom.service;

import com.telecom.model.Bill;
import com.telecom.model.Payment;
import com.telecom.model.PaymentStatus;
import com.telecom.repository.DataStore;
import com.telecom.util.ValidationException;

public class PaymentService {
    public Payment pay(Bill bill, String method) throws ValidationException {
        if (bill == null) throw new ValidationException("Select a bill.");
        if (method == null || method.isBlank()) throw new ValidationException("Select a payment method.");
        if (bill.getPaymentStatus() == PaymentStatus.PAID)
            throw new ValidationException("This bill is already paid.");

        Payment payment = new Payment("TXN-" + System.currentTimeMillis(),
                bill.getBillId(), bill.getTotalAmount(), method);
        bill.setPaymentStatus(PaymentStatus.PAID);
        DataStore.payments.add(payment);
        return payment;
    }
}

package com.telecom.service;

import com.telecom.model.Usage;
import com.telecom.repository.DataStore;
import com.telecom.util.ValidationException;
import com.telecom.util.Validator;
import java.time.LocalDate;

public class UsageService {
    public Usage add(String sim, LocalDate date, int calls, int sms, double data) throws ValidationException {
        if (!DataStore.simCustomerMap.containsKey(sim))
            throw new ValidationException("No customer is registered with this SIM.");
        Validator.validateUsage(calls, sms, data);
        Usage usage = new Usage(sim, date, calls, sms, data);
        DataStore.usageHistory.addFirst(usage);
        return usage;
    }
}

package com.telecom.service;

import com.telecom.model.*;
import com.telecom.repository.DataStore;
import com.telecom.util.ValidationException;
import com.telecom.util.Validator;

public class SIMService {
    public SIMCard activate(Customer customer, String number, Plan plan) throws ValidationException {
        Validator.validateSIM(number);
        if (plan == null) throw new ValidationException("Select a plan.");
        if (DataStore.simCustomerMap.containsKey(number))
            throw new ValidationException("This SIM number is already registered.");

        SIMCard sim = new SIMCard(number);
        sim.activate();
        DataStore.sims.add(sim);
        DataStore.simCustomerMap.put(number, customer);
        customer.setSimCard(sim);
        customer.setPlan(plan);
        return sim;
    }
}

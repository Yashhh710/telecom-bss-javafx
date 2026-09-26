package com.telecom.repository;

import com.telecom.model.*;
import java.time.LocalDate;
import java.util.*;

public final class DataStore {

    public static final ArrayList<Customer> customers = new ArrayList<>();
    public static final ArrayList<Plan> plans = new ArrayList<>();
    public static final ArrayList<SIMCard> sims = new ArrayList<>();
    public static final LinkedList<Usage> usageHistory = new LinkedList<>();
    public static final HashMap<String, Customer> simCustomerMap = new HashMap<>();
    public static final TreeMap<LocalDate, Bill> bills = new TreeMap<>();
    public static final ArrayList<Payment> payments = new ArrayList<>();

    private DataStore() {}

    public static void seed() {
        if (!customers.isEmpty()) return;

        Plan p149 = new Plan("P149", "Starter 149", PlanType.PREPAID, 149, 28, 2000, 100);
        Plan p199 = new Plan("P199", "Basic 199", PlanType.PREPAID, 199, 45, 3000, 100);
        Plan p299 = new Plan("P299", "Smart 299", PlanType.PREPAID, 299, 60, 5000, 100);
        Plan p399 = new Plan("P399", "Power 399", PlanType.PREPAID, 399, 70, 7500, 150);
        Plan p499 = new Plan("P499", "Max 499", PlanType.POSTPAID, 499, 50, 10000, 300);
        Plan p799 = new Plan("P799", "Premium 799", PlanType.POSTPAID, 799, 90, 20000, 500);

        plans.addAll(List.of(p149, p199, p299, p399, p499, p799));

        addCustomer(new Customer(1, "Yash Tambade", "yash@example.com", "9876543210", "Kharghar"));
        addCustomer(new Customer(2, "Rahul Sharma", "rahul@example.com", "9876543211", "Panvel"));
        addCustomer(new Customer(3, "Aman Patil", "aman@example.com", "9876543212", "Navi Mumbai"));
        addCustomer(new Customer(4, "Priya Deshmukh", "priya@example.com", "9876543213", "Vashi"));
        addCustomer(new Customer(5, "Rohan Mehta", "rohan@example.com", "9876543214", "Belapur"));
        addCustomer(new Customer(6, "Sneha Kulkarni", "sneha@example.com", "9876543215", "Kharghar"));
        addCustomer(new Customer(7, "Aditya Joshi", "aditya@example.com", "9876543216", "Panvel"));
        addCustomer(new Customer(8, "Neha Shah", "neha@example.com", "9876543217", "Kamothe"));
        addCustomer(new Customer(9, "Vivek More", "vivek@example.com", "9876543218", "Kalamboli"));
        addCustomer(new Customer(10, "Anjali Rao", "anjali@example.com", "9876543219", "Vashi"));
        addCustomer(new Customer(11, "Karan Singh", "karan@example.com", "9876543220", "Nerul"));
        addCustomer(new Customer(12, "Pooja Nair", "pooja@example.com", "9876543221", "Seawoods"));
        addCustomer(new Customer(13, "Arjun Kapoor", "arjun@example.com", "9876543222", "Airoli"));
        addCustomer(new Customer(14, "Isha Verma", "isha@example.com", "9876543223", "Ghansoli"));
        addCustomer(new Customer(15, "Manish Gupta", "manish@example.com", "9876543224", "Taloja"));
        addCustomer(new Customer(16, "Riya Malhotra", "riya@example.com", "9876543225", "Kharghar"));
        addCustomer(new Customer(17, "Sahil Khan", "sahil@example.com", "9876543226", "Panvel"));
        addCustomer(new Customer(18, "Tanvi Patil", "tanvi@example.com", "9876543227", "Kamothe"));
        addCustomer(new Customer(19, "Akash Jadhav", "akash@example.com", "9876543228", "Belapur"));
        addCustomer(new Customer(20, "Simran Kaur", "simran@example.com", "9876543229", "Nerul"));

        registerSim(customers.get(0), "9876543210", p299);
        registerSim(customers.get(1), "9876543211", p199);
        registerSim(customers.get(2), "9876543212", p499);
        registerSim(customers.get(3), "9876543213", p399);
        registerSim(customers.get(4), "9876543214", p799);
        registerSim(customers.get(5), "9876543215", p299);
        registerSim(customers.get(6), "9876543216", p149);
        registerSim(customers.get(7), "9876543217", p399);
        registerSim(customers.get(8), "9876543218", p199);
        registerSim(customers.get(9), "9876543219", p499);
        registerSim(customers.get(10), "9876543220", p799);
        registerSim(customers.get(11), "9876543221", p299);
        registerSim(customers.get(12), "9876543222", p399);
        registerSim(customers.get(13), "9876543223", p199);
        registerSim(customers.get(14), "9876543224", p499);
        registerSim(customers.get(15), "9876543225", p799);
        registerSim(customers.get(16), "9876543226", p149);
        registerSim(customers.get(17), "9876543227", p299);
        registerSim(customers.get(18), "9876543228", p399);
        registerSim(customers.get(19), "9876543229", p499);

        addUsage();
    }

    private static void addUsage() {
        usageHistory.add(new Usage("9876543210", LocalDate.now(), 120, 34, 2.5));
        usageHistory.add(new Usage("9876543210", LocalDate.now().minusDays(1), 80, 21, 1.8));
        usageHistory.add(new Usage("9876543211", LocalDate.now(), 65, 12, 1.2));
        usageHistory.add(new Usage("9876543211", LocalDate.now().minusDays(2), 45, 18, 0.9));
        usageHistory.add(new Usage("9876543212", LocalDate.now(), 180, 45, 4.7));
        usageHistory.add(new Usage("9876543213", LocalDate.now(), 95, 27, 2.1));
        usageHistory.add(new Usage("9876543214", LocalDate.now(), 210, 56, 6.4));
        usageHistory.add(new Usage("9876543215", LocalDate.now(), 75, 19, 1.7));
        usageHistory.add(new Usage("9876543216", LocalDate.now(), 35, 8, 0.6));
        usageHistory.add(new Usage("9876543217", LocalDate.now(), 140, 31, 3.8));
        usageHistory.add(new Usage("9876543218", LocalDate.now(), 55, 15, 1.1));
        usageHistory.add(new Usage("9876543219", LocalDate.now(), 165, 42, 4.2));
        usageHistory.add(new Usage("9876543220", LocalDate.now(), 230, 61, 7.1));
        usageHistory.add(new Usage("9876543221", LocalDate.now(), 90, 24, 2.3));
        usageHistory.add(new Usage("9876543222", LocalDate.now(), 125, 37, 3.1));
        usageHistory.add(new Usage("9876543223", LocalDate.now(), 70, 16, 1.5));
        usageHistory.add(new Usage("9876543224", LocalDate.now(), 190, 49, 5.3));
        usageHistory.add(new Usage("9876543225", LocalDate.now(), 250, 68, 8.2));
        usageHistory.add(new Usage("9876543226", LocalDate.now(), 40, 10, 0.8));
        usageHistory.add(new Usage("9876543227", LocalDate.now(), 110, 29, 2.7));
        usageHistory.add(new Usage("9876543228", LocalDate.now(), 155, 35, 3.9));
        usageHistory.add(new Usage("9876543229", LocalDate.now(), 175, 44, 4.5));
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    private static void registerSim(Customer customer, String number, Plan plan) {
        SIMCard sim = new SIMCard(number);
        sim.activate();

        sims.add(sim);
        customer.setSimCard(sim);
        customer.setPlan(plan);

        simCustomerMap.put(number, customer);
    }
}
package com.telecom.util;

public final class Validator {
    private Validator() {}

    public static void validateName(String value) throws ValidationException {
        if (value == null || value.isBlank()) throw new ValidationException("Customer name is required.");
    }

    public static void validatePhone(String value) throws ValidationException {
        if (value == null || !value.matches("\\d{10}"))
            throw new ValidationException("Phone number must contain exactly 10 digits.");
    }

    public static void validateEmail(String value) throws ValidationException {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new ValidationException("Enter a valid email address.");
    }

    public static void validateSIM(String value) throws ValidationException {
        if (value == null || !value.matches("\\d{10}"))
            throw new ValidationException("SIM number must contain exactly 10 digits.");
    }

    public static void validateUsage(int calls, int sms, double data) throws ValidationException {
        if (calls < 0 || sms < 0 || data < 0)
            throw new ValidationException("Usage values cannot be negative.");
    }

    public static void validatePlanCode(String value) throws ValidationException {
        if (value == null || !value.matches("[A-Za-z0-9-]{2,12}"))
            throw new ValidationException("Plan code must be 2-12 letters, numbers or hyphens.");
    }
}

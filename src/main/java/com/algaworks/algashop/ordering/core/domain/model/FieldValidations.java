package com.algaworks.algashop.ordering.core.domain.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    private FieldValidations() {
    }

    public static void requiresNonBlank(String value) {
        requiresNonBlank(value, null);
    }

    public static void requiresNonBlank(String value, String message) {
        Objects.requireNonNull(value, message);

        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }


    public static void requiresValidEmail(String email) {
        requiresValidEmail(email, null);
    }

    public static void requiresValidEmail(String email, String errorMessage) {
        requiresNonBlank(email, errorMessage);

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}

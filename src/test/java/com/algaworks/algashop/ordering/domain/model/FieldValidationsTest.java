package com.algaworks.algashop.ordering.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FieldValidationsTest {

    @Nested
    @DisplayName("Requires non blank")
    class RequiresNonBlank {

        @Test
        @DisplayName("Should pass when value is non-blank and no custom message is provided")
        void given_nonBlankValue_whenRequiresNonBlank_shouldNotThrowException() {
            Assertions.assertThatNoException()
                    .isThrownBy(() -> FieldValidations.requiresNonBlank("valid"));
        }

        @Test
        @DisplayName("Should throw NullPointerException when value is null")
        void given_nullValue_whenRequiresNonBlank_shouldThrowNullPointerException() {
            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> FieldValidations.requiresNonBlank(null));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when value is blank")
        void given_blankValue_whenRequiresNonBlank_shouldThrowIllegalArgumentException() {
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresNonBlank("   "));
        }

        @Test
        @DisplayName("Should throw NullPointerException with custom message when value is null")
        void given_nullValueWithCustomMessage_whenRequiresNonBlank_shouldThrowNullPointerExceptionWithMessage() {
            String message = "Field is required";
            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> FieldValidations.requiresNonBlank(null, message));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException with custom message when value is blank")
        void given_blankValueWithCustomMessage_whenRequiresNonBlank_shouldThrowIllegalArgumentExceptionWithMessage() {
            String message = "Field cannot be blank";
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresNonBlank(" ", message));
        }
    }

    @Nested
    @DisplayName("Requires valid e-mail")
    class RequiresValidEmail {

        @Test
        @DisplayName("Should pass when email is valid")
        void given_validEmail_whenRequiresValidEmail_shouldNotThrowException() {
            Assertions.assertThatNoException()
                    .isThrownBy(() -> FieldValidations.requiresValidEmail("user@example.com"));
        }

        @Test
        @DisplayName("Should throw NullPointerException when email is null")
        void given_nullEmail_whenRequiresValidEmail_shouldThrowNullPointerException() {
            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail(null));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when email is blank")
        void given_blankEmail_whenRequiresValidEmail_shouldThrowIllegalArgumentException() {
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail("  "));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when email is invalid")
        void given_invalidEmail_whenRequiresValidEmail_shouldThrowIllegalArgumentException() {
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail("invalid-email"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException with custom message when email is invalid")
        void given_invalidEmailWithCustomMessage_whenRequiresValidEmail_shouldThrowIllegalArgumentExceptionWithMessage() {
            String message = "Invalid email";
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail("invalid@", message));
        }

        @Test
        @DisplayName("Should throw NullPointerException with custom message when email is null")
        void given_nullEmailWithCustomMessage_whenRequiresValidEmail_shouldThrowNullPointerExceptionWithMessage() {
            String message = "Email is required";
            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail(null, message));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException with custom message when email is blank")
        void given_blankEmailWithCustomMessage_whenRequiresValidEmail_shouldThrowIllegalArgumentExceptionWithMessage() {
            String message = "Email cannot be blank";
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> FieldValidations.requiresValidEmail(" ", message));
        }
    }
}

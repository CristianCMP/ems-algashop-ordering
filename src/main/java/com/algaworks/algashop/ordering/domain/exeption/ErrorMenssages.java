package com.algaworks.algashop.ordering.domain.exeption;

public class ErrorMenssages {
    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";


    public static final String ERROR_CUSTOMER_ARQUIVED= "Customer is arquived it connot be changed";
    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGE= "Connot change order %s status from %s to %s";

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_IN_THE_PAST= "Order %s expected delivery date cannot be in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS = "Order %s cannot be placed, it has no items";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO = "Order %s cannot be placed, it has no shipping info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO = "Order %s cannot be placed, it has no billing info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD = "Order %s cannot be placed, it has no payment method";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_INVALID_SHIPPING_COST = "Order %s cannot be placed, it has no shipping cost";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_INVALID_DELIVERY_DATE = "Order %s cannot be placed, it has no delivery date";
}

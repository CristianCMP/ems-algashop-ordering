package com.algaworks.algashop.ordering.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllower;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime arquivedAt;
    private Integer loyaltyPoints;
}
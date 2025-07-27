package com.algaworks.algashop.ordering.domain.entity;

import lombok.EqualsAndHashCode;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    @EqualsAndHashCode.Include
    private UUID id;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllower;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime arquivedAt;
    private Integer loyaltyPoints;

    public Customer(UUID id, String fullName, LocalDate birthDate, String email, String phone, String document, Boolean promotionNotificationsAllower, OffsetDateTime registeredAt) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllower(promotionNotificationsAllower);
        this.setRegisteredAt(registeredAt);
        this.setArchived(false);
        this.setLoyaltyPoints(0);
    }

    public Customer(UUID id, String fullName, LocalDate birthDate, String email, String phone, String document, Boolean promotionNotificationsAllower, Boolean archived, OffsetDateTime registeredAt, OffsetDateTime arquivedAt, Integer loyaltyPoints) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllower(promotionNotificationsAllower);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArquivedAt(arquivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
    }

    public void addLoyaltyPoints(Integer points) {
    }

    public void archive() {
        this.setArchived(true);
    }

    public void enablePromotionNotifications() {
        this.setPromotionNotificationsAllower(true);
    }

    public void disablePromotionNotifications() {
        this.setPromotionNotificationsAllower(false);
    }

    public void changeName(String fullName) {
        this.setFullName(fullName);
    }

    public void changeEmail(String email) {
        this.setEmail(email);
    }

    public void changePhone(String phone) {
        this.setPhone(phone);
    }

    public UUID id() {
        return id;
    }

    public String fullName() {
        return fullName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllower() {
        return promotionNotificationsAllower;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime arquivedAt() {
        return arquivedAt;
    }

    public Integer loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    private void setFullName(String fullName) {
        Objects.requireNonNull(fullName);

        if (fullName.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }

        this.birthDate = birthDate;
    }

    private void setEmail(String email) {
        Objects.requireNonNull(email);

        if (email.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException();
        }

        this.email = email;
    }

    private void setPhone(String phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(String document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setPromotionNotificationsAllower(Boolean promotionNotificationsAllower) {
        Objects.requireNonNull(promotionNotificationsAllower);
        this.promotionNotificationsAllower = promotionNotificationsAllower;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArquivedAt(OffsetDateTime arquivedAt) {
        this.arquivedAt = arquivedAt;
    }

    private void setLoyaltyPoints(Integer loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }
}
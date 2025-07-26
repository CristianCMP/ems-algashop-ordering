package com.algaworks.algashop.ordering.domain.entity;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.OffsetDateTime;
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
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllower = promotionNotificationsAllower;
        this.registeredAt = registeredAt;
    }

    public Customer(UUID id, String fullName, LocalDate birthDate, String email, String phone, String document, Boolean promotionNotificationsAllower, Boolean archived, OffsetDateTime registeredAt, OffsetDateTime arquivedAt, Integer loyaltyPoints) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllower = promotionNotificationsAllower;
        this.archived = archived;
        this.registeredAt = registeredAt;
        this.arquivedAt = arquivedAt;
        this.loyaltyPoints = loyaltyPoints;
    }

    public void addLoyaltyPoints(Integer points) {
        this.loyaltyPoints += points;
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
        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    private void setDocument(String document) {
        this.document = document;
    }

    private void setPromotionNotificationsAllower(Boolean promotionNotificationsAllower) {
        this.promotionNotificationsAllower = promotionNotificationsAllower;
    }

    private void setArchived(Boolean archived) {
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    private void setArquivedAt(OffsetDateTime arquivedAt) {
        this.arquivedAt = arquivedAt;
    }

    private void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
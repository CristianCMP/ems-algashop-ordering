package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.CustomerArquivedExeption;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.VALIDATION_ERROR_EMAIL_IS_INVALID;
import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.VALIDATION_ERROR_FULLNAME_IS_NULL;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    @EqualsAndHashCode.Include
    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllower;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime arquivedAt;
    private LoyaltyPoints loyaltyPoints;
    private Address address;

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document, Boolean promotionNotificationsAllower, OffsetDateTime registeredAt, Address address) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllower(promotionNotificationsAllower);
        this.setRegisteredAt(registeredAt);
        this.setArchived(false);
        this.setLoyaltyPoints(LoyaltyPoints.ZERO);
        this.setAddress(address);
    }

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document, Boolean promotionNotificationsAllower, Boolean archived, OffsetDateTime registeredAt, OffsetDateTime arquivedAt, LoyaltyPoints loyaltyPoints, Address address) {
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
        this.setAddress(address);
    }

    public void addLoyaltyPoints(LoyaltyPoints loyaltyPointsAdded) {
        verifyIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints().add(loyaltyPointsAdded));
    }

    public void archive() {
        verifyIfChangeable();

        this.setArchived(true);
        this.setArquivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllower(false);
        this.setAddress(this.address().toBuilder()
                .number("Anonymized")
                .complement(null).build());
    }

    public void enablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllower(true);
    }

    public void disablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllower(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeable();
        this.setFullName(fullName);
    }

    public void changeEmail(Email email) {
        verifyIfChangeable();
        this.setEmail(email);
    }

    public void changePhone(Phone phone) {
        verifyIfChangeable();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeable();
        this.setAddress(address);

    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
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

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }

        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
        FieldValidations.requiresValidEmail(email.value());
        this.email = email;
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(Document document) {
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
        this.registeredAt = registeredAt;
    }

    private void setArquivedAt(OffsetDateTime arquivedAt) {
        this.arquivedAt = arquivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void setAddress(Address address) {
        Objects.requireNonNull(address);
        this.address = address;
    }

    private void verifyIfChangeable() {
        if (this.archived) {
            throw new CustomerArquivedExeption();
        }
    }
}
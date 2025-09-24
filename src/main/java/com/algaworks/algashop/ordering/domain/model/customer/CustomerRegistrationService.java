package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class CustomerRegistrationService {

    private final Customers customers;

    public Customer register(FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document, Boolean promotionNotificationsAllowed, Address address) {
        Customer customer = Customer.brandNew()
                .fullName(fullName)
                .birthDate(birthDate)
                .email(email)
                .phone(phone)
                .document(document)
                .promotionNotificationsAllowed(promotionNotificationsAllowed)
                .address(address)
                .build();

        verifyEmailUnique(customer.email(), customer.id());

        return customer;
    }

    public void changeEmail(Customer customer, Email newEmail) {
        verifyEmailUnique(newEmail, customer.id());
        customer.changeEmail(newEmail);
        customers.add(customer);
    }

    private void verifyEmailUnique(Email email, CustomerId customerId) {
        if (!customers.isEmailUnique(email, customerId)){
            throw new CustomerEmailsIsInUseException();
        }
    }
}

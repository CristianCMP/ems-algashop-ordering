package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
class CustomersIT {

    private final Customers customers;

    @Autowired
    public CustomersIT(Customers customers) {
        this.customers = customers;
    }

    @Test
    public void shouldPersistAndFind(){
        Customer originalCustomer = CustomerTestDataBuilder.existingCustomer().build();
        CustomerId customerId = originalCustomer.id();
        customers.add(originalCustomer);

        Optional<Customer> possibleCustomer = customers.ofId(customerId);

        assertThat(possibleCustomer).isPresent();

        Customer saveCustomer = possibleCustomer.get();

        assertThat(saveCustomer).satisfies(
                s -> assertThat(s.id()).isEqualTo(customerId),
                s -> assertThat(s.fullName()).isEqualTo(originalCustomer.fullName()),
                s -> assertThat(s.birthDate()).isEqualTo(originalCustomer.birthDate()),
                s -> assertThat(s.email()).isEqualTo(originalCustomer.email()),
                s -> assertThat(s.phone()).isEqualTo(originalCustomer.phone()),
                s -> assertThat(s.document()).isEqualTo(originalCustomer.document()),
                s -> assertThat(s.isPromotionNotificationsAllowed()).isEqualTo(originalCustomer.isPromotionNotificationsAllowed()),
                s -> assertThat(s.isArchived()).isEqualTo(originalCustomer.isArchived()),
                s -> assertThat(s.registeredAt()).isEqualTo(originalCustomer.registeredAt()),
                s -> assertThat(s.archivedAt()).isEqualTo(originalCustomer.archivedAt()),
                s -> assertThat(s.loyaltyPoints()).isEqualTo(originalCustomer.loyaltyPoints()),
                s -> assertThat(s.address()).isEqualTo(originalCustomer.address()),
                s -> assertThat(s.version()).isEqualTo(originalCustomer.version())
        );
    }

    @Test
    public void shouldCountExistingCustomers(){
        assertThat(customers.count()).isZero();

        Customer order1 = CustomerTestDataBuilder.existingCustomer().build();
        Customer order2 = CustomerTestDataBuilder.existingCustomer().build();

        customers.add(order1);
        customers.add(order2);

        assertThat(customers.count()).isEqualTo(2);
    }

    @Test
    public void shouldReturnIfCustomerExists() {
        Customer order = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(order);

        assertThat(customers.exists(order.id())).isTrue();
        assertThat(customers.exists(new CustomerId())).isFalse();
    }
}
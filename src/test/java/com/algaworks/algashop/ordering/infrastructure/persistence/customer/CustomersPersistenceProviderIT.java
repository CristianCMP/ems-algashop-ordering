package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.core.domain.model.commons.Email;
import com.algaworks.algashop.ordering.core.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.*;
import com.algaworks.algashop.ordering.infrastructure.config.auditing.SpringDataAuditingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class CustomersPersistenceProviderIT {

    private final CustomersPersistenceProvider persistenceProvider;
    private final CustomerPersistenceEntityRepository entityRepository;

    @Autowired
    public CustomersPersistenceProviderIT(CustomersPersistenceProvider persistenceProvider, CustomerPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.entityRepository = entityRepository;
    }

    @BeforeEach
    public void setUp() {
        entityRepository.deleteAll();
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        String originEmail = "user@email.com";
        Customer customer = CustomerTestDataBuilder.existingCustomer()
                .email(new Email(originEmail))
                .build();
        UUID customerId = customer.id().value();

        persistenceProvider.add(customer);

        CustomerPersistenceEntity customerPersistence = entityRepository.findById(customerId).orElseThrow();

        assertThat(customerPersistence).satisfies(
                cp -> assertThat(cp.getEmail()).isEqualTo(originEmail),

                cp -> assertThat(cp.getCreatedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedAt()).isNotNull()
        );

        String newEmail = "new@email.com";

        customer = persistenceProvider.ofId(customer.id()).orElseThrow();
        customer.changeEmail(new Email(newEmail));
        persistenceProvider.add(customer);

        customerPersistence = entityRepository.findById(customerId).orElseThrow();

        assertThat(customerPersistence).satisfies(
                cp -> assertThat(cp.getEmail()).isEqualTo(newEmail),

                cp -> assertThat(cp.getCreatedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedAt()).isNotNull()
        );
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddAndFindNotFailWhenNoTransaction() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        persistenceProvider.add(customer);

        assertThatNoException().isThrownBy(() -> persistenceProvider.ofId(customer.id()).orElseThrow());
    }

    @Test
    void shouldCountCorrectly() {
        entityRepository.deleteAll();
        assertThat(persistenceProvider.count()).isZero();

        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        persistenceProvider.add(customer);

        assertThat(persistenceProvider.count()).isEqualTo(1L);
    }

    @Test
    void shouldVerifyIfExists() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        CustomerId customerId = customer.id();

        assertThat(persistenceProvider.exists(customerId)).isFalse();

        persistenceProvider.add(customer);

        assertThat(persistenceProvider.exists(customerId)).isTrue();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void shouldAddFindAndNotFailWhenNoTransaction(){
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        persistenceProvider.add(customer);

        Customer savedCustomer = persistenceProvider.ofId(customer.id()).orElseThrow();

        assertThat(savedCustomer).isNotNull();
    }
}
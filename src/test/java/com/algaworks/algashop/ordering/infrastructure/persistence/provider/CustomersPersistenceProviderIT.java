package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
package com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.AbstractPersistenceIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest // load the full Spring application context and all beans.
//@Transactional  // @DataJpaTest has @Transactional inside by default.
//@DataJpaTest  // configuration only JPA.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // do not change the database's configure.
//@Import(SpringDataAuditingConfig.class)
class CustomerPersistenceEntityRepositoryIT extends AbstractPersistenceIT {

    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @Autowired
    public CustomerPersistenceEntityRepositoryIT(CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @Test
    public void shouldPersist() {
        CustomerPersistenceEntity entity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();

        customerPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(customerPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        CustomerPersistenceEntity savedEntity = customerPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        assertThat(entity).isEqualTo(savedEntity);
    }

    @Test
    public void shouldCount() {
        long customersCoung = customerPersistenceEntityRepository.count();
        assertThat(customersCoung).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        CustomerPersistenceEntity entity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();

        entity = customerPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(entity.getCreatedByUserId()).isNotNull();
        assertThat(entity.getLastModifiedAt()).isNotNull();
        assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }
}
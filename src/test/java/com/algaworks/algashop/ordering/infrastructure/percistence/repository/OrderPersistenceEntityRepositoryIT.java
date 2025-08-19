package com.algaworks.algashop.ordering.infrastructure.percistence.repository;

import com.algaworks.algashop.ordering.domain.model.utitly.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.percistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest // load the full Spring application context and all beans.
//@Transactional  // @DataJpaTest has @Transactional inside by default.
@DataJpaTest  // configuration only JPA.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // do not change the database's configure.
@Import(SpringDataAuditingConfig.class)
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
    }

    @Test
    public void shouldPersist() {
        OrderPersistenceEntity entity = OrderPersistenceTestDataBuilder.existingOrder().build();
        long orderId = entity.getId();

        orderPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(orderPersistenceEntityRepository.existsById(orderId)).isTrue();
    }

    @Test
    public void shouldCount() {
        long ordersCoung = orderPersistenceEntityRepository.count();
        assertThat(ordersCoung).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceTestDataBuilder.existingOrder().build();

        entity = orderPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(entity.getCreatedByUserId()).isNotNull();
        assertThat(entity.getLastModifiedAt()).isNotNull();
        assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }
}
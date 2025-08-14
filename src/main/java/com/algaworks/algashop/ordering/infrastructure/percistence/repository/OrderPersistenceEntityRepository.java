package com.algaworks.algashop.ordering.infrastructure.percistence.repository;

import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {
}

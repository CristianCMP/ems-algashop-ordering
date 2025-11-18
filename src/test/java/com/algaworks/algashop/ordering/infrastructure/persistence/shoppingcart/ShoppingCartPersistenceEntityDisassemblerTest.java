package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.commons.Money;
import com.algaworks.algashop.ordering.core.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCartId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartPersistenceEntityDisassemblerTest {

    private final ShoppingCartPersistenceEntityDisassembler disassembler = new ShoppingCartPersistenceEntityDisassembler();

    @Test
    public void shouldConvertFromPersistenceEntity() {
        ShoppingCartPersistenceEntity persistenceEntity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart().build();

        ShoppingCart domainEntity = disassembler.toDomainEntity(persistenceEntity);

        assertThat(domainEntity).satisfies(
                s -> assertThat(s.id()).isEqualTo(new ShoppingCartId(persistenceEntity.getId())),
                s -> assertThat(s.customerId()).isEqualTo(new CustomerId(persistenceEntity.getCustomer().getId())),
                s -> assertThat(s.totalItems()).isEqualTo(new Quantity(persistenceEntity.getTotalItems())),
                s -> assertThat(s.totalAmount()).isEqualTo(new Money(persistenceEntity.getTotalAmount())),
                s -> assertThat(s.createdAt()).isEqualTo(persistenceEntity.getCreatedAt()),
                s -> assertThat(s.items()).hasSize(persistenceEntity.getItems().size())
        );
    }
}

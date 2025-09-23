package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShoppingCartPersistenceEntityAssemblerTest {

    @Mock
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @InjectMocks
    private ShoppingCartPersistenceEntityAssembler assembler;

    @BeforeEach
    public void setUp() {
        Mockito
                .when(customerPersistenceEntityRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(a -> {
                    UUID customerId = a.getArgument(0, UUID.class);
                    return CustomerPersistenceEntityTestDataBuilder.aCustomer().id(customerId).build();
                });
    }

    @Test
    void shouldToDomain() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = assembler.fromDomain(shoppingCart);

        assertThat(shoppingCartPersistenceEntity).satisfies(
                p -> assertThat(p.getId()).isEqualTo(shoppingCart.id().value()),
                p -> assertThat(p.getCustomerId()).isEqualTo(shoppingCart.customerId().value()),
                p -> assertThat(p.getTotalAmount()).isEqualTo(shoppingCart.totalAmount().value()),
                p -> assertThat(p.getTotalItems()).isEqualTo(shoppingCart.totalItems().value()),
                p -> assertThat(p.getCreatedAt()).isEqualTo(shoppingCart.createdAt())
        );
    }

    @Test
    void givenShoppingCartWithNotItems_shouldRemovePersistenceEntityItems() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart().build();

        assertThat(shoppingCart.items()).isEmpty();
        assertThat(shoppingCartPersistenceEntity.getItems()).isNotEmpty();

        assembler.merge(shoppingCartPersistenceEntity, shoppingCart);

        assertThat(shoppingCartPersistenceEntity.getItems()).isEmpty();
    }

    @Test
    void givenShoppingCartWithItems_shouldAddToPersistenceEntityItems() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(true).build();
        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart().items(new HashSet<>()).build();

        assertThat(shoppingCart.items()).isNotEmpty();
        assertThat(shoppingCartPersistenceEntity.getItems()).isEmpty();

        assembler.merge(shoppingCartPersistenceEntity, shoppingCart);

        assertThat(shoppingCartPersistenceEntity.getItems()).isNotEmpty();
        assertThat(shoppingCartPersistenceEntity.getItems().size()).isEqualTo(shoppingCart.items().size());
    }

    @Test
    void givenShoppingCartWithItems_shouldRemovedMergeCorrectly() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(true).build();

        assertThat(shoppingCart.items().size()).isEqualTo(2);

        Set<ShoppingCartItemPersistenceEntity> shoppingCartItemPersistenceEntities = shoppingCart.items()
                .stream()
                .map(assembler::toOrderItemsEntities)
                .collect(Collectors.toSet());

        ShoppingCartPersistenceEntity persistenceEntity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .items(shoppingCartItemPersistenceEntities)
                .build();

        ShoppingCartItem shoppingCartItem = shoppingCart.items().iterator().next();
        shoppingCart.removeItem(shoppingCartItem.id());

        assembler.merge(persistenceEntity, shoppingCart);

    }
}
package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShoppingCartPersistenceEntityAssembler {

    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    public ShoppingCartPersistenceEntity fromDomain(ShoppingCart shoppingCart) {
        return merge(new ShoppingCartPersistenceEntity(), shoppingCart);
    }

    public ShoppingCartPersistenceEntity merge(ShoppingCartPersistenceEntity shoppingCartPersistenceEntity, ShoppingCart shoppingCart) {
        shoppingCartPersistenceEntity.setId(shoppingCart.id().value());
        shoppingCartPersistenceEntity.setTotalAmount(shoppingCart.totalAmount().value());
        shoppingCartPersistenceEntity.setTotalItems(shoppingCart.totalItems().value());
        shoppingCartPersistenceEntity.setCreatedAt(shoppingCart.createdAt());
        shoppingCartPersistenceEntity.setItems(mergeItems(shoppingCart,shoppingCartPersistenceEntity));

        CustomerPersistenceEntity customerPersistenceEntity = customerPersistenceEntityRepository.getReferenceById(shoppingCart.customerId().value());
        shoppingCartPersistenceEntity.setCustomer(customerPersistenceEntity);

        return shoppingCartPersistenceEntity;
    }

    private Set<ShoppingCartItemPersistenceEntity> mergeItems(ShoppingCart shoppingCart, ShoppingCartPersistenceEntity shoppingCartPersistenceEntity) {
        Set<ShoppingCartItem> newOrUpdatedItems = shoppingCart.items();

        if (newOrUpdatedItems == null || newOrUpdatedItems.isEmpty()) {
            return new HashSet<>();
        }

        Set<ShoppingCartItemPersistenceEntity> existingItems = shoppingCartPersistenceEntity.getItems();
        if (existingItems == null || existingItems.isEmpty()) {
            return newOrUpdatedItems
                    .stream()
                    .map(this::fromDomain)
                    .collect(Collectors.toSet());
        }

        Map<UUID, ShoppingCartItemPersistenceEntity> existingItemsMap = existingItems
                .stream()
                .collect(Collectors.toMap(ShoppingCartItemPersistenceEntity::getId, item -> item));

        return newOrUpdatedItems
                .stream()
                .map(orderItem -> {
                    ShoppingCartItemPersistenceEntity itemPersistence = existingItemsMap.getOrDefault(
                            orderItem.id().value(), new ShoppingCartItemPersistenceEntity()
                    );

                    return merge(itemPersistence, orderItem);
                })
                .collect(Collectors.toSet());
    }

    public ShoppingCartItemPersistenceEntity fromDomain(ShoppingCartItem shoppingCartItem) {
        return merge(new ShoppingCartItemPersistenceEntity(), shoppingCartItem);
    }

    private ShoppingCartItemPersistenceEntity merge(ShoppingCartItemPersistenceEntity shoppingCartItemPersistenceEntity, ShoppingCartItem shoppingCartItem) {
        shoppingCartItemPersistenceEntity.setId(shoppingCartItem.id().value());
        shoppingCartItemPersistenceEntity.setProductId(shoppingCartItem.productId().value());
        shoppingCartItemPersistenceEntity.setName(shoppingCartItem.name().value());
        shoppingCartItemPersistenceEntity.setPrice(shoppingCartItem.price().value());
        shoppingCartItemPersistenceEntity.setQuantity(shoppingCartItem.quantity().value());
        shoppingCartItemPersistenceEntity.setTotalAmount(shoppingCartItem.totalAmount().value());
        shoppingCartItemPersistenceEntity.setAvailable(shoppingCartItem.isAvailable());

        return shoppingCartItemPersistenceEntity;
    }
}

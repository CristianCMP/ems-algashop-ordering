package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.http;

import com.algaworks.algashop.ordering.core.domain.model.commons.Money;
import com.algaworks.algashop.ordering.core.domain.model.product.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ResilientProductCatalogAPIClient productCatalogAPIClient;

    @ConcurrencyLimit(10) // The @ConcurrencyLimit always executes before @Retryable
    @Retryable(
            maxRetries = 3,
            delayString = "3s", // 1° = 3s, 2° = 6s, 3° = 12s
            multiplier = 2,
            includes = {GatewayTimeoutException.class, BadGatewayException.class}
    )
    @Override
    public Optional<Product> ofId(ProductId productId) {
        return productCatalogAPIClient.getById(productId.value())
                .map(productResponse ->
                        Product.builder()
                                .id(new ProductId(productResponse.getId()))
                                .name(new ProductName(productResponse.getName()))
                                .inStock(productResponse.getInStock())
                                .price(new Money(productResponse.getSalePrice()))
                                .build()
        );
    }
}
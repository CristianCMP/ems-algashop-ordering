package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.http;

import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResilientProductCatalogAPIClient {

    private final ProductCatalogAPIClient productCatalogAPIClient;

//    Spring execution order: @Cacheable, @ConcurrencyLimit, and @Retryable
    @Cacheable(cacheNames = "algashop:product-catalog-api:v1",key = "#productId")
    @ConcurrencyLimit(10) // The @ConcurrencyLimit always executes before @Retryable
    @Retryable(
            maxRetries = 3,
            delayString = "3s", // 1° = 3s, 2° = 6s, 3° = 12s
            multiplier = 2,
            includes = {GatewayTimeoutException.class, BadGatewayException.class}
    )
    public Optional<ProductResponse> getById(UUID productId) {
        log.info("Loading product {}",productId);
        try {
          return Optional.of(productCatalogAPIClient.getById(productId));
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException("Product Catalog API Timeout", e);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (RestClientException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new GatewayTimeoutException("Product Catalog API Timeout", e);
            }
            throw new BadGatewayException("Product Catalog API Bad Gateway", e);
        }
    }
}
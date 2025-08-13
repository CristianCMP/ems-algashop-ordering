package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.ERROR_PRODUCT_IS_OUT_STOCK;

public class ProductOutOfStockException extends DomainExeption {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ERROR_PRODUCT_IS_OUT_STOCK, id));
    }
}

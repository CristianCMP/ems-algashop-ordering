package com.algaworks.algashop.ordering.domain.model.exeption;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.ERROR_PRODUCT_IS_OUT_STOCK;

public class ProductOutOfStockException extends DomainExeption {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ERROR_PRODUCT_IS_OUT_STOCK, id));
    }
}

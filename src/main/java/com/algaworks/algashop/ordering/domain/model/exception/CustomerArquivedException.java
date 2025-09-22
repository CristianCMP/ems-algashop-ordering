package com.algaworks.algashop.ordering.domain.model.exception;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMenssages.ERROR_CUSTOMER_ARQUIVED;

public class CustomerArquivedException extends DomainException {

    public CustomerArquivedException(Throwable cause) {
        super(ERROR_CUSTOMER_ARQUIVED, cause);
    }

    public CustomerArquivedException() {
        super(ERROR_CUSTOMER_ARQUIVED);
    }
}

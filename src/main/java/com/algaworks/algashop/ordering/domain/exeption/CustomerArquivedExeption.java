package com.algaworks.algashop.ordering.domain.exeption;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.ERROR_CUSTOMER_ARQUIVED;

public class CustomerArquivedExeption extends DomainExeption{

    public CustomerArquivedExeption(Throwable cause) {
        super(ERROR_CUSTOMER_ARQUIVED, cause);
    }

    public CustomerArquivedExeption() {
        super(ERROR_CUSTOMER_ARQUIVED);
    }
}

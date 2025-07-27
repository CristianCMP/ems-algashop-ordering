package com.algaworks.algashop.ordering.domain.exeption;

public class DomainExeption extends RuntimeException{

    public DomainExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainExeption(String message) {
        super(message);
    }
}

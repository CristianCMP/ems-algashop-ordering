package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityAssembler {

    public CustomerPersistenceEntity fromDomain(Customer aggregateRoot) {
        return merge(new CustomerPersistenceEntity(), aggregateRoot);
    }

    public CustomerPersistenceEntity merge(CustomerPersistenceEntity customerPersistenceEntity, Customer customer) {
        customerPersistenceEntity.setId(customer.id().value());
        customerPersistenceEntity.setFirstName(customer.fullName().firstName());
        customerPersistenceEntity.setLastName(customer.fullName().lastName());
        customerPersistenceEntity.setBirthDate(customer.birthDate().value());
        customerPersistenceEntity.setEmail(customer.email().value());
        customerPersistenceEntity.setPhone(customer.phone().value());
        customerPersistenceEntity.setDocument(customer.document().value());
        customerPersistenceEntity.setPromotionNotificationsAllowed(customer.isPromotionNotificationsAllowed());
        customerPersistenceEntity.setArchived(customer.isArchived());
        customerPersistenceEntity.setRegisteredAt(customer.registeredAt());
        customerPersistenceEntity.setArchivedAt(customer.archivedAt());
        customerPersistenceEntity.setLoyaltyPoints(customer.loyaltyPoints().value());
        customerPersistenceEntity.setAddress(toAddressEmbeddable(customer.address()));
        customerPersistenceEntity.setVersion(customer.version()); //Here it is optional

        return customerPersistenceEntity;
    }

    public static AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) return null;
        return AddressEmbeddable.builder()
                .city(address.city())
                .complement(address.complement())
                .street(address.street())
                .state(address.state())
                .neighborhood(address.neighborhood())
                .number(address.number())
                .zipCode(address.zipCode().value())
                .build();
    }

}

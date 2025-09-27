package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerEmailsIsInUseException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Test
    public void shouldRegister() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();

        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        assertThat(customerOutput)
                .extracting(
                        CustomerOutput::getId,
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getEmail,
                        CustomerOutput::getBirthDate
                ).containsExactly(
                        customerId,
                        "John",
                        "Doe",
                        "johndoe@email.com",
                        LocalDate.of(1991, 7,5)
                );

        assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }

    @Test
    public void shouldUpdate() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        CustomerUpdateInput updateInput = CustomerUpdateInputTestDataBuilder.aCustomerUpdate().build();

        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.update(customerId, updateInput);

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        assertThat(customerOutput)
                .extracting(
                        CustomerOutput::getId,
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getEmail,
                        CustomerOutput::getBirthDate
                ).containsExactly(
                        customerId,
                        "Matt",
                        "Damon",
                        "johndoe@email.com",
                        LocalDate.of(1991, 7,5)
                );

        assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }

    @Test
    public void shouldArchiveCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        CustomerOutput archivedCustomer = customerManagementApplicationService.findById(customerId);

        assertThat(archivedCustomer)
                .isNotNull()
                .extracting(
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getPhone,
                        CustomerOutput::getDocument,
                        CustomerOutput::getBirthDate,
                        CustomerOutput::getPromotionNotificationsAllowed
                ).containsExactly(
                        "Anonymous",
                        "Anonymous",
                        "000-000-0000",
                        "000-00-0000",
                        null,
                        false
                );

        assertThat(archivedCustomer.getEmail()).endsWith("@anonymous.com");
        assertThat(archivedCustomer.getArchived()).isTrue();
        assertThat(archivedCustomer.getArchivedAt()).isNotNull();

        assertThat(archivedCustomer.getAddress()).isNotNull();
        assertThat(archivedCustomer.getAddress().getNumber()).isNotNull().isEqualTo("Anonymized");
        assertThat(archivedCustomer.getAddress().getComplement()).isNull();
    }

    @Test
    public void shouldThrowCustomerNotFoundExceptionWhenArchivingNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(nonExistingId));
    }

    @Test
    public void shouldThrowCustomerArchivedExceptionWhenArchivingAlreadyArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(customerId));
    }

    @Test
    public void shouldChangeEmailSuccessfully() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);

        String newEmail = "newemail@email.com";
        customerManagementApplicationService.changeEmail(customerId, newEmail);

        CustomerOutput updatedCustomer = customerManagementApplicationService.findById(customerId);

        assertThat(updatedCustomer)
                .isNotNull()
                .extracting(CustomerOutput::getId, CustomerOutput::getEmail)
                .containsExactly(customerId, newEmail);
    }

    @Test
    public void shouldThrowCustomerNotFoundExceptionWhenChangingEmailOfNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(nonExistingId, "any@email.com"));
    }

    @Test
    public void shouldThrowCustomerArchivedExceptionWhenChangingEmailOfArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);

        customerManagementApplicationService.archive(customerId);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "archived@email.com"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenChangingToInvalidEmailFormat() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "email-invalido"));
    }

    @Test
    public void shouldThrowCustomerEmailsIsInUseExceptionWhenChangingToExistingEmail() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer()
                .email("customer1@email.com")
                .build();
        UUID customer1Id = customerManagementApplicationService.create(customerInput);

        CustomerInput input2 = CustomerInputTestDataBuilder.aCustomer()
                .email("customer2@email.com")
                .firstName("Jane")
                .lastName("Doe")
                .build();

        UUID customer2Id = customerManagementApplicationService.create(input2);

        assertThatExceptionOfType(CustomerEmailsIsInUseException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customer1Id, "customer2@email.com"));
    }


}
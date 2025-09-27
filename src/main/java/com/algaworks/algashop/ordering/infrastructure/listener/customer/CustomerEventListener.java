package com.algaworks.algashop.ordering.infrastructure.listener.customer;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventListener {

    @EventListener
    public void listen(CustomerEventListener event){
        log.info("CustomerEventListener listen 1");
    }

    @EventListener
    public void listenSecondary(CustomerEventListener event){
        log.info("CustomerEventListener listen 2");
    }

    @EventListener
    public void listen(CustomerArchivedEvent event){
        log.info("CustomerArchivedEvent listen 1");
    }
}

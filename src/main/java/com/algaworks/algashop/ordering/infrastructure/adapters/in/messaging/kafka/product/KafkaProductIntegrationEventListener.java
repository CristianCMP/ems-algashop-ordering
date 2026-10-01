package com.algaworks.algashop.ordering.infrastructure.adapters.in.messaging.kafka.product;

import com.algaworks.algashop.ordering.core.application.product.event.ProductDelistedIntegrationEvent;
import com.algaworks.algashop.ordering.core.application.product.event.ProductListedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tools.jackson.databind.node.ObjectNode;

@Component
@Slf4j
@KafkaListener(topics = {"product-catalog.product.events"})
public class KafkaProductIntegrationEventListener {

    @KafkaHandler(isDefault = true)
    public void handle(@Payload Object event,
//                       @Payload ConsumerRecord<String, ObjectNode> consumerRecord, // Another way to use it,instead of using Object
                       @Header(value = KafkaHeaders.RECEIVED_KEY) String messageKey,
                       @Header(value = KafkaHeaders.OFFSET) String messageOffset
    ) {
        log.info("Event ignored: key={} offset={}", messageKey, messageOffset);
    }

    @KafkaHandler
    public void handle(@Payload ProductListedIntegrationEvent event,
                       @Header(value = KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received " + event.getClass());
        log.info("MessageKey " + messageKey);
    }

    @KafkaHandler
    public void handle(@Payload ProductDelistedIntegrationEvent event,
                       @Header(value = KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received " + event.getClass());
        log.info("MessageKey " + messageKey);
    }

}
package dev.dkorez.msathesis.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dkorez.msathesis.catalog.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class InventoryEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @Inject
    public InventoryEventConsumer(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @Incoming("inventory-events")
    public CompletionStage<Void> processUpdates(String event) {
        try {
            logger.info("incoming inventory-events: {}", event);
            InventoryEvent inventoryEvent = objectMapper.readValue(event, InventoryEvent.class);

            switch (inventoryEvent.getType()) {
                case INVENTORY_UPDATED -> productService.updateQuantity(inventoryEvent.getProductId(), inventoryEvent.getQuantity());
                case RESERVATION_RELEASED, INVENTORY_RESERVED -> {
                    //TODO: implement reserved quantity
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("error processing product-event: {}", e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}

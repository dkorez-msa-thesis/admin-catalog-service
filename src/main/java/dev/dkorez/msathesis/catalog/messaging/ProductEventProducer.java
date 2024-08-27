package dev.dkorez.msathesis.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductEventProducer {
    private final Logger logger = LoggerFactory.getLogger(ProductEventProducer.class);

    Emitter<Record<String, String>> emitter;
    ObjectMapper objectMapper;

    @Inject
    public ProductEventProducer(@Channel("product-events") Emitter<Record<String, String>> emitter,
                                ObjectMapper objectMapper) {
        this.emitter = emitter;
        this.objectMapper = objectMapper;
    }

    public void sendEvent(ProductEvent event) {
        try {
            String jsonEvent = objectMapper.writeValueAsString(event);
            emitter.send(Record.of(event.getProductId().toString(), jsonEvent));

            logger.info("outgoing product-update: {}", event);
        }
        catch (JsonProcessingException e) {
            logger.error("could not serialize product event {}: {}", event.getProductId(), e.getMessage(), e);
        }
    }
}

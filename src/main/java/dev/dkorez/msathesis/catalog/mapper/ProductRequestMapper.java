package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.ProductDao;
import dev.dkorez.msathesis.catalog.model.ProductRequestDto;

public class ProductRequestMapper {
    public static ProductDao toEntity(ProductRequestDto product) {
        ProductDao entity = new ProductDao();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setActive(product.isActive());

        return entity;
    }
}

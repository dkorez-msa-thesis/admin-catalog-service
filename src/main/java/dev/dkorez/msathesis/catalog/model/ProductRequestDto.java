package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean active;
    private Long categoryId;
    private Long brandId;
    private List<Long> specs;
    private List<Long> tags;
}

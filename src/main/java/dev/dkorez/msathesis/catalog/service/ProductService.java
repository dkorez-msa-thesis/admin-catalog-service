package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.ProductDto;
import dev.dkorez.msathesis.catalog.entity.*;
import dev.dkorez.msathesis.catalog.exception.NotFoundException;
import dev.dkorez.msathesis.catalog.mapper.ProductMapper;
import dev.dkorez.msathesis.catalog.mapper.ProductRequestMapper;
import dev.dkorez.msathesis.catalog.messaging.ProductEvent;
import dev.dkorez.msathesis.catalog.messaging.ProductEventProducer;
import dev.dkorez.msathesis.catalog.messaging.ProductEventType;
import dev.dkorez.msathesis.catalog.model.ProductRequestDto;
import dev.dkorez.msathesis.catalog.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private BrandRepository brandRepository;

    @Inject
    private SpecificationRepository specificationRepository;

    @Inject
    private TagRepository tagRepository;

    @Inject
    private ProductEventProducer eventProducer;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          BrandRepository brandRepository,
                          SpecificationRepository specificationRepository,
                          TagRepository tagRepository,
                          ProductEventProducer eventProducer) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.specificationRepository = specificationRepository;
        this.tagRepository = tagRepository;
        this.eventProducer = eventProducer;
    }

    public List<ProductDto> getProducts() {
        return productRepository.listAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long id) {
        ProductDao entity = productRepository.findById(id);
        return ProductMapper.toDto(entity);
    }

    public ProductDto create(ProductRequestDto product) {
        ProductDao entity = createProductInDb(product);

        long timestamp = Instant.now().toEpochMilli();
        ProductEvent event = new ProductEvent(ProductEventType.CREATED, entity.getId(), ProductMapper.toDto(entity), timestamp);
        eventProducer.sendEvent(event);
        return ProductMapper.toDto(entity);
    }

    public ProductDto update(Long id, ProductRequestDto product) {
        ProductDao entity = updateProductInDb(id, product);

        long timestamp = Instant.now().toEpochMilli();
        ProductEvent event = new ProductEvent(ProductEventType.UPDATED, entity.getId(), ProductMapper.toDto(entity), timestamp);
        eventProducer.sendEvent(event);

        return ProductMapper.toDto(entity);
    }

    public void updateQuantity(Long id, int quantity) {
        ProductDto response = updateQuantityInDb(id, quantity);

        if (response != null) {
            long timestamp = Instant.now().toEpochMilli();
            ProductEvent event = new ProductEvent(ProductEventType.UPDATED, response.getId(), response, timestamp);
            eventProducer.sendEvent(event);
        }
    }

    private ProductDto updateQuantityInDb(Long id, int quantity) {
        ProductDao entity = productRepository.findById(id);
        if (quantity == entity.getQuantity())
            return null;

        entity.setQuantity(quantity);
        entity.setUpdatedAt(LocalDateTime.now());
        productRepository.persist(entity);

        return ProductMapper.toDto(entity);
    }

    public void delete(Long id) {
        deleteProductInDb(id);

        long timestamp = Instant.now().toEpochMilli();
        ProductEvent event = new ProductEvent(ProductEventType.DELETED, id, null, timestamp);
        eventProducer.sendEvent(event);
    }

    @Transactional
    public ProductDao createProductInDb(ProductRequestDto product) {
        ProductDao entity = ProductRequestMapper.toEntity(product);

        if (product.getCategoryId() != null) {
            Optional<CategoryDao> categoryOpt = categoryRepository.findByIdOptional(product.getCategoryId());
            categoryOpt.ifPresent(entity::setCategory);
        }
        if (product.getBrandId() != null) {
            Optional<BrandDao> brandOpt = brandRepository.findByIdOptional(product.getBrandId());
            brandOpt.ifPresent(entity::setBrand);
        }
        if (product.getSpecs() != null && !product.getSpecs().isEmpty()) {
            List<SpecificationDao> specs = specificationRepository.findByIdMap(product.getSpecs());
            entity.setSpecification(specs);
        }
        if (product.getTags() != null && !product.getTags().isEmpty()) {
            List<TagDao> tags = product.getTags().stream()
                    .map(tagId -> tagRepository.findByIdOptional(tagId).orElseThrow(() ->
                            new NotFoundException("Tag not found")))
                    .collect(Collectors.toList());
            entity.setTags(tags);
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        productRepository.persist(entity);
        return entity;
    }

    @Transactional
    public ProductDao updateProductInDb(Long id, ProductRequestDto product) {
        ProductDao entity = productRepository.findByIdOptional(id).orElseThrow(() ->
                new NotFoundException("product " + id + " not found"));
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setActive(product.isActive());
        entity.setUpdatedAt(LocalDateTime.now());

        if (product.getCategoryId() != null) {
            Optional<CategoryDao> categoryOpt = categoryRepository.findByIdOptional(product.getCategoryId());
            categoryOpt.ifPresent(entity::setCategory);
        }

        if (product.getBrandId() != null) {
            Optional<BrandDao> brandOpt = brandRepository.findByIdOptional(product.getBrandId());
            brandOpt.ifPresent(entity::setBrand);
        }

        if (product.getSpecs() != null && !product.getSpecs().isEmpty()) {
            List<SpecificationDao> specs = specificationRepository.findByIdMap(product.getSpecs());
            entity.setSpecification(specs);
        }

        if (product.getTags() != null && !product.getTags().isEmpty()) {
            List<TagDao> tags = product.getTags().stream()
                    .map(tagId -> tagRepository.findByIdOptional(tagId).orElseThrow(() ->
                            new NotFoundException("Tag not found")))
                    .collect(Collectors.toList());
            entity.setTags(tags);
        }

        productRepository.persist(entity);
        return entity;
    }

    @Transactional
    public void deleteProductInDb(Long id) {
        productRepository.deleteById(id);
    }
}

package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.CategoryDto;
import dev.dkorez.msathesis.catalog.entity.CategoryDao;
import dev.dkorez.msathesis.catalog.mapper.CategoryMapper;
import dev.dkorez.msathesis.catalog.mapper.CategoryRequestMapper;
import dev.dkorez.msathesis.catalog.model.CategoryRequestDto;
import dev.dkorez.msathesis.catalog.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Inject
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.listAll().stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategory(Long id) {
        CategoryDao entity = categoryRepository.findById(id);
        return CategoryMapper.toDto(entity);
    }

    @Transactional
    public CategoryDto create(CategoryRequestDto category) {
        CategoryDao entity = CategoryRequestMapper.toEntity(category);

        categoryRepository.persist(entity);
        return CategoryMapper.toDto(entity);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryRequestDto category) {
        CategoryDao entity = categoryRepository.findById(id);
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());

        categoryRepository.persist(entity);
        return CategoryMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}

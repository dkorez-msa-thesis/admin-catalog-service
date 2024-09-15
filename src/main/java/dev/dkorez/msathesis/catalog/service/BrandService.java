package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.BrandDto;
import dev.dkorez.msathesis.catalog.entity.BrandDao;
import dev.dkorez.msathesis.catalog.mapper.BrandMapper;
import dev.dkorez.msathesis.catalog.model.BrandRequestDto;
import dev.dkorez.msathesis.catalog.repository.BrandRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BrandService {
    private final BrandRepository brandRepository;

    @Inject
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDto> getBrands() {
        return brandRepository.listAll().stream()
                .map(BrandMapper::toDto)
                .toList();
    }

    public BrandDto getBrand(Long id) {
        BrandDao entity = brandRepository.findById(id);
        return BrandMapper.toDto(entity);
    }

    @Transactional
    public BrandDto create(BrandRequestDto brand) {
        BrandDao entity = new BrandDao();
        entity.setName(brand.getName());
        entity.setDescription(brand.getDescription());

        brandRepository.persist(entity);
        return BrandMapper.toDto(entity);
    }

    @Transactional
    public BrandDto update(Long id, BrandRequestDto brand) {
        BrandDao entity = brandRepository.findById(id);
        entity.setName(brand.getName());
        entity.setDescription(brand.getDescription());

        brandRepository.persist(entity);
        return BrandMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }
}

package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.SpecificationDto;
import dev.dkorez.msathesis.catalog.entity.SpecificationDao;
import dev.dkorez.msathesis.catalog.mapper.SpecificationMapper;
import dev.dkorez.msathesis.catalog.model.SpecsRequestDto;
import dev.dkorez.msathesis.catalog.repository.SpecificationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SpecificationService {
    @Inject
    private SpecificationRepository specificationRepository;

    public List<SpecificationDto> getSpecifications() {
        return specificationRepository.listAll().stream()
                .map(SpecificationMapper::toDto)
                .toList();
    }

    public SpecificationDto getSpecification(Long id) {
        SpecificationDao entity = specificationRepository.findById(id);
        return SpecificationMapper.toDto(entity);
    }

    @Transactional
    public void create(SpecsRequestDto spec) {
        SpecificationDao entity = new SpecificationDao();
        entity.setName(spec.getName());
        entity.setValue(spec.getValue());

        specificationRepository.persist(entity);
    }

    @Transactional
    public SpecificationDto update(Long id, SpecsRequestDto spec) {
        SpecificationDao entity = specificationRepository.findById(id);
        entity.setName(spec.getName());
        entity.setValue(spec.getValue());

        specificationRepository.persist(entity);
        return SpecificationMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        specificationRepository.deleteById(id);
    }
}

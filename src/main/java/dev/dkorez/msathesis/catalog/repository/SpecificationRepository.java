package dev.dkorez.msathesis.catalog.repository;

import dev.dkorez.msathesis.catalog.entity.SpecificationDao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SpecificationRepository implements PanacheRepository<SpecificationDao> {
    public List<SpecificationDao> findByIdMap(List<Long> idMap) {
        return list("id IN ?1", idMap);
    }
}

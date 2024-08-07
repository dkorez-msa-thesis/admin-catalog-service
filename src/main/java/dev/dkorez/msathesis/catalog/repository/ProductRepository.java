package dev.dkorez.msathesis.catalog.repository;

import dev.dkorez.msathesis.catalog.entity.ProductDao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductDao> {

}

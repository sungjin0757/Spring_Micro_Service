package com.example.catalogservice.repository;

import com.example.catalogservice.domain.CatalogEntity;
import org.springframework.data.repository.CrudRepository;


public interface CatalogRepository extends CrudRepository<CatalogEntity,Long> {
    CatalogEntity findByProductId(String productId);
}

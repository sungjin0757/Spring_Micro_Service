package com.example.catalogservice.service;


import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.dto.CatalogDto;

import java.util.List;

public interface CatalogService {
    List<CatalogDto> getAllCatalogs();
}

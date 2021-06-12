package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status(){

        return String.format("It's Working, I'm Catalog-Service on PORT %s"
                ,env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        List<CatalogDto> catalogByAll = catalogService.getAllCatalogs();
        List<ResponseCatalog> collect = catalogByAll.stream()
                .map(c -> new ResponseCatalog(c.getProductId(),c.getProductName(),c.getUnitPrice(),c.getStock(),c.getCreatedAt()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(collect);
    }
}

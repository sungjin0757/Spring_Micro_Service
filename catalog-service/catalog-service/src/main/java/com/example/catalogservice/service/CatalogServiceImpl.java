package com.example.catalogservice.service;

import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogDto> getAllCatalogs() {
        Iterable<CatalogEntity> all = catalogRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<CatalogDto> dtos=new ArrayList<>();
        all.forEach(a->{
            dtos.add(mapper.map(a,CatalogDto.class));
        });

        return dtos;
    }
}

package com.example.orderservice.service;


import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) {
        Iterable<OrderEntity> entities = orderRepository.findByUserId(userId);
        List<OrderDto> dtos=new ArrayList<>();

        entities.forEach(e->{
            dtos.add(new ModelMapper().map(e,OrderDto.class));
        });

        return dtos;
    }

    @Override
    public OrderDto getOrdersByOrderId(String orderId) {
        OrderEntity entity = orderRepository.findByOrderId(orderId);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto map = mapper.map(entity, OrderDto.class);

        return map;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getStock()*orderDto.getUnitPrice());

        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity map = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(map);

        OrderDto dto = mapper.map(map, OrderDto.class);

        return dto;
    }
}

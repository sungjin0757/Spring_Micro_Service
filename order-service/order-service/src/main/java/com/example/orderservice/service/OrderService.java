package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(String userId);
    OrderDto getOrdersByOrderId(String orderId);
    OrderDto createOrder(OrderDto orderDto);
}

package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status(){

        return String.format("It's Working, I'm User-Service on PORT %s"
                ,env.getProperty("local.server.port"));
    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createUser(@RequestBody RequestOrder order,@PathVariable("userId")String userId){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder=mapper.map(createOrder,ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getUsers(@PathVariable("userId")String userId){
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> responseOrders=new ArrayList<>();

        List<ResponseOrder> collect = orders.stream()
                .map(o -> new ResponseOrder(o.getProductId(), o.getUnitPrice(), o.getTotalPrice(), o.getStock(), o.getCreatedAt(), o.getOrderId()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(collect);
    }

}

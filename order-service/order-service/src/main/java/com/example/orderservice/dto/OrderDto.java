package com.example.orderservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderDto implements Serializable {
    private String productId;

    private Integer stock;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
    private String userId;
}

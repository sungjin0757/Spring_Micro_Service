package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ResponseOrder {
    private String productId;

    private Integer unitPrice;
    private Integer totalPrice;
    private Integer stock;
    private Date createdAt;

    private String orderId;
    public ResponseOrder() {
    }
}

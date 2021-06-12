package com.example.orderservice.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class RequestOrder {
    @NotNull
    private String productId;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer unitPrice;

}

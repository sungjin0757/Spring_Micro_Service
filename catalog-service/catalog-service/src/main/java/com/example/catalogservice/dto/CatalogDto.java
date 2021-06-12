package com.example.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CatalogDto implements Serializable {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
    private String userId;
}

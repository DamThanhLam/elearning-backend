package com.elearning.elearning_sdk.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "order_products")
public class OrderProduct {

    @Id
    private String id;

    @Field("order_id")
    private String orderId;

    @Field("product_id")
    private String productId;

    @Field("product_name")
    private String productName;

    private int quantity;

    private BigDecimal price;

    @Field("original_price")
    private BigDecimal originalPrice;
}

package com.codecool.shop.model;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class Order extends BaseModel {
    private final LocalDateTime orderTime;
    private final String orderTimeString;
    private final List<Product> productsPurchased;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Order(List<Product> productsPurchased) {
        this.orderTime = LocalDateTime.now();
        this.productsPurchased = productsPurchased;
        this.orderTimeString = orderTime.format(formatter);
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public String getOrderTimeString() {
        return orderTimeString;
    }
}

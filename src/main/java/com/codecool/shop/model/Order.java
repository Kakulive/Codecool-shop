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
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String firstName;
    private String lastName;
    private String email;
    private String billingAddress;
    private String shippingAddress;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


}

package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.List;

public interface CartDao {

    void add(Product product);
    Cart find(int id);
    void remove(Product product);
    List<Product> getAll();
}

package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartDaoMem implements CartDao {

    List<Product> cartItems = new ArrayList<>();
    private static CartDaoMem instance = null;

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        cartItems.add(product);
    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public void remove(Product product) {
        cartItems.remove(product);
    }

    @Override
    public List<Product> getAll() {
        return cartItems;
    }

    @Override
    public HashMap<Product, Integer> getCartItemsQuantities() {
        HashMap<Product, Integer> cartItemsQuantities = new HashMap<>();
        for (Product item : cartItems) {
            if (!cartItemsQuantities.containsKey(item)) {
                cartItemsQuantities.put(item, 1);
            } else {
                cartItemsQuantities.put(item, cartItemsQuantities.get(item) + 1);
            }
        }
        return cartItemsQuantities;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("0");
        for (Product item : cartItems) {
            totalPrice = totalPrice.add(item.getDefaultPrice());
        }
        return totalPrice;
    }

    @Override
    public void updateCart(Product changedProduct, int quantity) {
        HashMap<Product, Integer> cartItemQuantities = getCartItemsQuantities();
        int itemsAlreadyInCart = cartItemQuantities.get(changedProduct);

        if (itemsAlreadyInCart == quantity) {
            return;
        }

        int quantityDifference = Math.abs(itemsAlreadyInCart - quantity);
        if (quantity <= 0) {
            for (int i = 0; i < itemsAlreadyInCart; i++) {
                cartItems.remove(changedProduct);
            }
        } else if (quantity > 0 && quantity < itemsAlreadyInCart) {
            for (int i = 0; i < quantityDifference; i++) {
                cartItems.remove(changedProduct);
            }
        } else {
            for (int i = 0; i < quantityDifference; i++) {
                cartItems.add(changedProduct);
            }
        }

    }


}

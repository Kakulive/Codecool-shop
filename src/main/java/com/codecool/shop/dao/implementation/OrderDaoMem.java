package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private List<Order> allOrders = new ArrayList<>();
    private static OrderDaoMem instance = null;

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        int orderId = allOrders.size()+1;
        order.setId(orderId);
        allOrders.add(order);
    }

    @Override
    public void remove(Order order) {
        allOrders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return allOrders;
    }
}

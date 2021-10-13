package com.codecool.shop.service;

import com.codecool.shop.dao.*;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private CartDao cartDao;
    private OrderDao orderDao;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao,
                          CartDao cartDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.cartDao = cartDao;
        this.orderDao = orderDao;
    }

    public ProductCategory getDefaultProductCategory() {
        return productCategoryDao.getDefaultCategory();
    }

    public ProductCategory getProductCategory(int categoryId) {
        return productCategoryDao.find(categoryId);
    }

    public ProductCategory getProductCategoryByName(String name) {
        return productCategoryDao.findByName(name);
    }

    public List<Product> getProductsForCategory(int categoryId) {
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<ProductCategory> getAllCategories() {
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAll();
    }

    public List<Supplier> getSuppliersForCategory(ProductCategory category) {
        List<Product> categoryProducts = getProductsForCategory(category.getId());
        List<Supplier> categorySuppliers = new ArrayList<>();
        for (Product product : categoryProducts) {
            if (!categorySuppliers.contains(product.getSupplier())) {
                categorySuppliers.add(product.getSupplier());
            }
        }
        return categorySuppliers;
    }

    public void addProductToCart(Product product) {
        cartDao.add(product);
    }

    public Product getProductByName(String name) {
        return productDao.find(name);
    }

    public HashSet<Product> getCartItemsWithoutDuplicates() {
        List<Product> allCartItems = cartDao.getAll();
        HashSet<Product> singleCartItems = new HashSet<>(allCartItems);
        return singleCartItems;
    }

    public List<Product> getAllCartItems() {
        return cartDao.getAll();
    }

    public HashMap<Product, Integer> getCartItemsQuantities() {
        return cartDao.getCartItemsQuantities();
    }

    public BigDecimal getTotalPrice() {
        return cartDao.getTotalPrice();
    }

    public void updateCart(String productName, int quantity){
        Product changedProduct = productDao.find(productName);
        cartDao.updateCart(changedProduct, quantity);
    }

    public void saveOrder(Order order){
        orderDao.add(order);
    }

    public void saveOrderToJson(Order order) throws IOException {
        String filePath = "C:\\Users\\Kakuszakak\\Desktop\\TEST\\order_" + order.getId() + ".json";
        FileWriter writer = new FileWriter(filePath);
        gson.toJson(order, writer);
        writer.flush();
        writer.close();
    }

}

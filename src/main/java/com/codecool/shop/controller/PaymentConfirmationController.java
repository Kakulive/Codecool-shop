package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@WebServlet(urlPatterns = {"/payment-confirmation"})
public class PaymentConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        ProductService productService = new ProductService(productDataStore, productCategoryDataStore,
                supplierDataStore, cartDataStore, orderDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        BigDecimal totalPrice = productService.getTotalPrice();
        context.setVariable("totalPrice", totalPrice);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        HashSet<Product> cartItemsWithoutDuplicates = productService.getCartItemsWithoutDuplicates();
        context.setVariable("cartItemsWithoutDuplicates", cartItemsWithoutDuplicates);

        HashMap<Product, Integer> cartItemsQuantities = productService.getCartItemsQuantities();
        context.setVariable("cartItemsQuantities", cartItemsQuantities);

        Order order = new Order(allCartItems);
        productService.saveOrder(order);
        context.setVariable("order", order);
        productService.saveOrderToJson(order);

        engine.process("product/paymentConfirmation.html", context, resp.getWriter());
    }

}

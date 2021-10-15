package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore,
                supplierDataStore, cartDataStore, orderDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        sendBillingDataToHtml(req, context);

        sendShippingDataToHtml(req, context);

        BigDecimal totalPrice = productService.getTotalPrice();
        context.setVariable("totalPrice", totalPrice);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        String paypalClientId = System.getenv("client_id");
        context.setVariable("paypalClientId", paypalClientId);

        Order order = new Order(allCartItems);
        productService.saveOrder(order);
        int currentOrderId = order.getId();
        productService.setCurrentOrderId(currentOrderId);
        fillOrderShippingData(req, order);

        engine.process("product/payment.html", context, resp.getWriter());
    }

    private void sendShippingDataToHtml(HttpServletRequest req, WebContext context) {
        setVariableFromFormDataField(req, context, "shippingInputAddress1");
        setVariableFromFormDataField(req, context, "shippingInputAddress2");
        setVariableFromFormDataField(req, context, "shippingInputCountry");
        setVariableFromFormDataField(req, context, "shippingInputCity");
        setVariableFromFormDataField(req, context, "shippingInputState");
        setVariableFromFormDataField(req, context, "shippingInputZip");
    }

    private void sendBillingDataToHtml(HttpServletRequest req, WebContext context) {
        setVariableFromFormDataField(req, context, "inputName");
        setVariableFromFormDataField(req, context, "inputSurname");
        setVariableFromFormDataField(req, context, "inputEmail");
        setVariableFromFormDataField(req, context, "inputPassword");
        setVariableFromFormDataField(req, context, "billingInputAddress1");
        setVariableFromFormDataField(req, context, "billingInputAddress2");
        setVariableFromFormDataField(req, context, "billingInputCountry");
        setVariableFromFormDataField(req, context, "billingInputCity");
        setVariableFromFormDataField(req, context, "billingInputState");
        setVariableFromFormDataField(req, context, "billingInputZip");
    }

    private void fillOrderShippingData(HttpServletRequest req, Order order) {
        order.setFirstName(req.getParameter("inputName"));
        order.setLastName(req.getParameter("inputSurname"));
        order.setEmail(req.getParameter("inputEmail"));
        order.setBillingAddress(req.getParameter("billingInputAddress1") + " " +
                req.getParameter("billingInputAddress2") + " " +
                req.getParameter("billingInputCountry") + " " +
                req.getParameter("billingInputCity") + " " +
                req.getParameter("billingInputState") + " " +
                req.getParameter("billingInputZip"));
        order.setShippingAddress(req.getParameter("shippingInputAddress1") + " " +
                req.getParameter("shippingInputAddress2") + " " +
                req.getParameter("shippingInputCountry") + " " +
                req.getParameter("shippingInputCity") + " " +
                req.getParameter("shippingInputState") + " " +
                req.getParameter("shippingInputZip"));
    }

    private void setVariableFromFormDataField(HttpServletRequest req, WebContext context, String fieldName) {
        context.setVariable(fieldName, req.getParameter("fieldName"));
    }

}

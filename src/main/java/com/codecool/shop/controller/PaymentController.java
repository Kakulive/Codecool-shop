package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
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
import java.util.Map;

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

        //Billing data
        setVariableFromFormDataField(req, context, "billingInputName");
        setVariableFromFormDataField(req, context, "inputSurname");
        setVariableFromFormDataField(req, context, "inputEmail");
        setVariableFromFormDataField(req, context, "inputPassword");
        setVariableFromFormDataField(req, context, "billingInputAddress1");
        setVariableFromFormDataField(req, context, "billingInputAddress2");
        setVariableFromFormDataField(req, context, "billingInputCountry");
        setVariableFromFormDataField(req, context, "billingInputCity");
        setVariableFromFormDataField(req, context, "billingInputState");
        setVariableFromFormDataField(req, context, "billingInputZip");

        //Shipping data
        setVariableFromFormDataField(req, context, "shippingInputAddress1");
        setVariableFromFormDataField(req, context, "shippingInputAddress2");
        setVariableFromFormDataField(req, context, "shippingInputCountry");
        setVariableFromFormDataField(req, context, "shippingInputCity");
        setVariableFromFormDataField(req, context, "shippingInputState");
        setVariableFromFormDataField(req, context, "shippingInputZip");

        BigDecimal totalPrice = productService.getTotalPrice();
        context.setVariable("totalPrice", totalPrice);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        String paypalClientId = System.getenv("client_id");
        context.setVariable("paypalClientId", paypalClientId);

        engine.process("product/payment.html", context, resp.getWriter());
    }

    private void setVariableFromFormDataField(HttpServletRequest req, WebContext context, String fieldName) {
        context.setVariable(fieldName, req.getParameter("fieldName"));
    }

}

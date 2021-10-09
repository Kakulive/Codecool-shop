package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
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
import java.util.*;

@WebServlet(urlPatterns = {"/"})
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDao = CartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore,
                supplierDataStore, cartDao);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductCategory defaultCategory = productService.getDefaultProductCategory();
        List<Product> defaultCategoryProducts = productService.getProductsForCategory(defaultCategory.getId());
        context.setVariable("defaultCategory", defaultCategory);
        context.setVariable("defaultCategoryProducts",defaultCategoryProducts);

        List<ProductCategory> allCategories = productService.getAllCategories();
        HashMap<ProductCategory,List<Product>> categoriesWithProducts = new HashMap<>();

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        for (ProductCategory category : allCategories){
            categoriesWithProducts.put(category,productService.getProductsForCategory(category.getId()));
        }

        context.setVariable("categoriesWithProducts", categoriesWithProducts);

        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDao = CartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore,
                supplierDataStore, cartDao);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductCategory defaultCategory = productService.getDefaultProductCategory();
        List<Product> defaultCategoryProducts = productService.getProductsForCategory(defaultCategory.getId());
        context.setVariable("defaultCategory", defaultCategory);
        context.setVariable("defaultCategoryProducts",defaultCategoryProducts);

        List<ProductCategory> allCategories = productService.getAllCategories();
        HashMap<ProductCategory,List<Product>> categoriesWithProducts = new HashMap<>();

        for (ProductCategory category : allCategories){
            categoriesWithProducts.put(category,productService.getProductsForCategory(category.getId()));
        }

        context.setVariable("categoriesWithProducts", categoriesWithProducts);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        String addedProductName = req.getParameter("productName");
        if (!Objects.equals(addedProductName, "")){
            Product addedProduct = productService.getProductByName(addedProductName);
            productService.addProductToCart(addedProduct);
        }

        engine.process("product/index.html", context, resp.getWriter());
    }

}

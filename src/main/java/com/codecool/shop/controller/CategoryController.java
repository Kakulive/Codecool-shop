package com.codecool.shop.controller;

import com.codecool.shop.Utils.FilteringAssistant;
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
import com.codecool.shop.model.Supplier;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = {"/category"})
public class CategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore,
                supplierDataStore, cartDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryName = req.getParameter("name");
        context.setVariable("categoryName", categoryName);

        ProductCategory selectedCategory = productService.getProductCategoryByName(categoryName);
        context.setVariable("category", selectedCategory);

        List<Product> selectedCategoryProducts = productService.getProductsForCategory(selectedCategory.getId());
        context.setVariable("categoryProducts", selectedCategoryProducts);

        List<Supplier> categorySuppliersList = productService.getSuppliersForCategory(selectedCategory);
        context.setVariable("suppliersList", categorySuppliersList);

        List<Supplier> selectedCategorySuppliers = productService.getSuppliersForCategory(selectedCategory);
        context.setVariable("selectedCategorySuppliers", selectedCategorySuppliers);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        engine.process("product/category.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore,
                supplierDataStore, cartDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryName = req.getParameter("name");
        context.setVariable("categoryName", categoryName);

        ProductCategory selectedCategory = productService.getProductCategoryByName(categoryName);
        context.setVariable("category", selectedCategory);

        List<Product> selectedCategoryProducts = productService.getProductsForCategory(selectedCategory.getId());
        context.setVariable("categoryProducts", selectedCategoryProducts);

        List<Supplier> categorySuppliersList = productService.getSuppliersForCategory(selectedCategory);
        context.setVariable("suppliersList", categorySuppliersList);

        List<Supplier> allSupplierList = productService.getAllSuppliers();
        List<Supplier> selectedCategorySuppliers = FilteringAssistant.getSuppliersFromCheckbox(req, allSupplierList);

        String addedProductName = req.getParameter("productName");
        if (!Objects.equals(addedProductName, null)){
            Product addedProduct = productService.getProductByName(addedProductName);
            productService.addProductToCart(addedProduct);
            selectedCategorySuppliers = categorySuppliersList;
        }

        context.setVariable("selectedCategorySuppliers", selectedCategorySuppliers);

        List<Product> allCartItems = productService.getAllCartItems();
        context.setVariable("cartItems", allCartItems);

        engine.process("product/category.html", context, resp.getWriter());
    }

}

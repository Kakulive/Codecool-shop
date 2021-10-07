package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private CartDao cartDao;

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao,
                          CartDao cartDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.cartDao = cartDao;
    }

    public ProductCategory getDefaultProductCategory(){
        return productCategoryDao.getDefaultCategory();
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    public ProductCategory getProductCategoryByName(String name){
        return productCategoryDao.findByName(name);
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<ProductCategory> getAllCategories(){
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSuppliers(){
        return supplierDao.getAll();
    }

    public List<Supplier> getSuppliersForCategory(ProductCategory category){
        List<Product> categoryProducts = getProductsForCategory(category.getId());
        List<Supplier> categorySuppliers = new ArrayList<>();
        for (Product product : categoryProducts){
            if (!categorySuppliers.contains(product.getSupplier())){
                categorySuppliers.add(product.getSupplier());
            }
        }
        return categorySuppliers;
    }


}

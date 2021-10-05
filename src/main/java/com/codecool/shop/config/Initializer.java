package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier blue = new Supplier("Blue", "Audio");
        supplierDataStore.add(blue);
        Supplier elgato = new Supplier("Elgato", "Streaming");
        supplierDataStore.add(blue);

        //setting up a new product category
        ProductCategory tablets = new ProductCategory("Tablets", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory microphones = new ProductCategory("Microphones", "Hardware", "Get the best microphones on the market.");
        productCategoryDataStore.add(tablets);
        productCategoryDataStore.add(microphones);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablets, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablets, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablets, amazon));
        productDataStore.add(new Product("Blue Yeti 3-Capsule USB Microphone - Slate", new BigDecimal("239"), "USD", "Create unparalleled recordings directly in your computer with Blue Microphone's Yeti USB Microphone. The Yeti uses Blue Microphone's proprietary tri-capsule technology to produce pristine, studio-quality recordings. It features four different pattern settings so you can record vocals, instrumental music, podcasts, or interviews in ways that would normally require multiple microphones. With simple controls for headphone volume, pattern selection, instant mute, and microphone gain, you'll be creating exceptional recordings right out of the box.", microphones, blue));
        productDataStore.add(new Product("Blue Snowball Professional USB Microphone - Black", new BigDecimal("139"), "USD", "Create studio-quality recordings with Blue Microphone's Snowball, a USB microphone with exceptional sound-capture capabilities. Using Blue Microphone's award-winning dual-capsule and circuit design, the Snowball produces crisp, clean recordings that are free of feedback and distortion. A unique pattern switch allows you to toggle between three different settings, giving you the flexibility to record vocals, instrumental music, podcasts, interviews, and more. With a plug-and-play design that is both Mac and PC compatible, the Snowball requires no additional drivers and is ready to record straight out of the box.", microphones, blue));
        productDataStore.add(new Product("Elgato WAVE:3 Premium Microphone", new BigDecimal("289"), "USD", "Quality content requires excellent sound. You need a broadcast-grade microphone that plugs directly into your setup, and a mixer to blend audio sources with ease. Meet Wave:3 — your complete solution.", microphones, elgato));
    }
}

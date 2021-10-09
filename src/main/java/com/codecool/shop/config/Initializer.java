package com.codecool.shop.config;

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
        CartDao cartDao = CartDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier blue = new Supplier("Blue", "Audio");
        supplierDataStore.add(blue);
        Supplier elgato = new Supplier("Elgato", "Streaming");
        supplierDataStore.add(elgato);
        Supplier razer = new Supplier("Razer", "Peripherals");
        supplierDataStore.add(razer);
        Supplier microsoft = new Supplier("Microsoft", "Peripherals");
        supplierDataStore.add(microsoft);
        Supplier logitech = new Supplier("Logitech", "Peripherals");
        supplierDataStore.add(logitech);
        Supplier asus = new Supplier("Asus", "Peripherals");
        supplierDataStore.add(asus);
        Supplier msi = new Supplier("MSI", "Peripherals");
        supplierDataStore.add(msi);
        Supplier corsair = new Supplier("Corsair", "Peripherals");
        supplierDataStore.add(corsair);
        Supplier roccat = new Supplier("Roccat", "Peripherals");
        supplierDataStore.add(roccat);

        //setting up a new product category
        ProductCategory tablets = new ProductCategory("Tablets", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory microphones = new ProductCategory("Microphones", "Hardware", "Get the best microphones on the market.");
        ProductCategory webcams = new ProductCategory("Webcams", "Hardware", "Awesome cameras, cheap price!");
        ProductCategory mice = new ProductCategory("Mice", "Hardware", "Tiny little sluggers, but useful.");
        ProductCategory keyboards = new ProductCategory("Keyboards", "Hardware", "Small, medium, big, we've got you covered.");
        productCategoryDataStore.add(tablets);
        productCategoryDataStore.add(microphones);
        productCategoryDataStore.add(webcams);
        productCategoryDataStore.add(mice);
        productCategoryDataStore.add(keyboards);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "USD",
                "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablets, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"),
                "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablets, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"),
                "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablets, amazon));
        productDataStore.add(new Product("Blue Yeti 3-Capsule USB Microphone - Slate", new BigDecimal("239"),
                "USD", "Create unparalleled recordings directly in your computer with Blue Microphone's Yeti USB Microphone.", microphones, blue));
        productDataStore.add(new Product("Blue Snowball Professional USB Microphone - Black", new BigDecimal("139"),
                "USD", "Create studio-quality recordings with Blue Microphone's Snowball, a USB microphone with exceptional sound-capture capabilities.", microphones, blue));
        productDataStore.add(new Product("Elgato WAVE 3 Premium Microphone", new BigDecimal("289"),
                "USD", "Quality content requires excellent sound. You need a broadcast-grade microphone that plugs directly into your setup, and a mixer to blend audio sources with ease. Meet Wave:3 — your complete solution.", microphones, elgato));
        productDataStore.add(new Product("Razer Kiyo Pro Webcam", new BigDecimal("349"),
                "USD", "USB Camera with High-Performance Adaptive Light Sensor.", webcams, razer));
        productDataStore.add(new Product("MICROSOFT WEBCAM LIFECAM HD-3000", new BigDecimal("79"),
                "USD", "This true HD camera gives you bright and colourful video, and makes it easy to post to your favourite social sites.", webcams, microsoft));
        productDataStore.add(new Product("Logitech StreamCam Full HD USB-C Webcam - Graphite", new BigDecimal("319"),
                "USD", "Logitech StreamCam Full HD USB-C Webcam - Graphite - Connection: USB 3.1 Gen 1 Type-C (1.5 Metre Cable) - Max Video.", webcams, logitech));
        productDataStore.add(new Product("Razer DeathAdder V2 Pro Wireless", new BigDecimal("189"),
                "USD", "Wireless gaming mouse with best-in-class ergonomics.", mice, razer));
        productDataStore.add(new Product("ASUS ROG Pugio II RGB Ambidextrous Wireless", new BigDecimal("219"),
                "USD", "The ambidextrous ROG Pugio II gaming mouse has leveled up to become premier choice for the fastest, fittest and finest gamers.", mice, asus));
        productDataStore.add(new Product("MSI Clutch GM50 Gaming Mouse", new BigDecimal("89"),
                "USD", "Perfect for rapid movements and clicks thanks to lightweight design and highly durable gaming switches rated for 20 million clicks.", mice, msi));
        productDataStore.add(new Product("ASUS ROG Strix Scope Deluxe RGB Mechanical Gaming Keyboard", new BigDecimal("389"),
                "USD", "Extend your battlefield prowess with ROG Strix Scope, the mechanical gaming keyboard with Xccurate Design – an extra-wide Ctrl key for FPS precision.", keyboards, asus));
        productDataStore.add(new Product("Corsair K100 RGB Mechanical Gaming Keyboard", new BigDecimal("569"),
                "USD", "The K100 RGB’s refined design features a durable aluminium frame and dynamic per-key RGB backlighting accented by a 44-zone three-sided RGB LightEdge.", keyboards, corsair));
        productDataStore.add(new Product("ROCCAT Vulcan TKL Compact Mechanical RGB Gaming Keyboard", new BigDecimal("229"),
                "USD", "The ROCCAT® Vulcan TKL Compact Mechanical RGB Gaming Keyboard presents the award-winning Titan Switch Mechanical in the smaller form factor.", keyboards, roccat));
        productDataStore.add(new Product("Roccat Magma Gaming Keyboard", new BigDecimal("109"),
                "USD", "The ROCCAT® Magma Membrane RGB Gaming Keyboard is ideal for RGBA enthusiasts seeking a competitive gaming keyboard.", keyboards, roccat));
        productDataStore.add(new Product("Corsair M65 RGB Elite Tunable FPS Gaming Mouse", new BigDecimal("139"),
                "USD", "EXTREME PRECISION, ELITE PERFORMANCE", mice, corsair));
        productDataStore.add(new Product("ROCCAT Burst Pro Extreme Lightweight Optical Pro Gaming Mouse", new BigDecimal("89"),
                "USD", "Extreme Lightweight Optical Pro Gaming Mouse", mice, roccat));
        productDataStore.add(new Product("Elgato WAVE 1 Premium Microphone", new BigDecimal("209"),
                "USD", "Premium Microphone and Digital Mixing Solution", microphones, elgato));
        productDataStore.add(new Product("LOGITECH C930E BUSINESS WEBCAM", new BigDecimal("259"),
                "USD", "Designed for business, a 1080p webcam with wide field of view and digital zoom", webcams, logitech));
        productDataStore.add(new Product("Logitech Brio 4K Pro Webcam\n", new BigDecimal("399"),
                "USD", "4K webcam with HDR and Windows Hello support", webcams, logitech));
    }
}

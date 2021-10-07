package com.codecool.shop.Utils;

import com.codecool.shop.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FilteringAssistant {

    public static List<Supplier> getSuppliersFromCheckbox (HttpServletRequest req, List<Supplier> allSupplierList){
        List<Supplier> selectedSuppliers = new ArrayList<>();
        for (Supplier supplier : allSupplierList){
            if (req.getParameterMap().containsKey(supplier.getName())){
                selectedSuppliers.add(supplier);
            }
        }
        return  selectedSuppliers;
    }
}

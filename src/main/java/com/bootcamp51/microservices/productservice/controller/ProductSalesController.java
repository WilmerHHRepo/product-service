package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.dto.ProductSalesDTO;
import com.bootcamp51.microservices.productservice.service.utils.ProductSalesServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class ProductSalesController {

    @Autowired
    private ProductSalesServiceUtil productSalesServiceUtil;

    @PatchMapping("/{document}")
    public ResponseEntity<Client> productSales(@RequestBody ProductSalesDTO productSalesRequest, @PathVariable String document) throws Exception {
        ProductSales productSales = productSalesRequest.getProductSales();
        JointAccount jointAccount =  productSalesRequest.getJointAccount();
        Client client = productSalesServiceUtil.productSales(productSales, jointAccount, document);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }
}

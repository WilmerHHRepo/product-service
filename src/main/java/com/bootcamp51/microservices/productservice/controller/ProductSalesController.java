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
@RequestMapping("/bootcamp51/ms/product/sales")
public class ProductSalesController {

    @Autowired
    private ProductSalesServiceUtil productSalesServiceUtil;

    @PatchMapping
    public ResponseEntity<Client> productSales(@RequestBody ProductSalesDTO productSalesRequest) throws Exception {
        Client client = productSalesRequest.getClient();
        ProductSales productSales = productSalesRequest.getNewProductSales();
        JointAccount jointAccount =  productSalesRequest.getJointAccount();
        client = productSalesServiceUtil.productSales(client, productSales, jointAccount);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }
}

package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.ClientProductSalesRequest;
import com.bootcamp51.microservices.productservice.service.utils.ProductSalesServiceUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bootcamp51/ms/product/sales")
public class ProductSalesController {

    @Autowired
    private ProductSalesServiceUtil productSalesServiceUtil;

    @PostMapping
    public ResponseEntity<Client> productSales(@RequestBody ClientProductSalesRequest productSalesRequest ){
        Client client = productSalesRequest.getClient();
        ProductSales productSales = productSalesRequest.getNewProductSales();
        ObjectId id =  client.getId();
        client = productSalesServiceUtil.productSales(client, productSales, id);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }



}

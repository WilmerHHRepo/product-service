package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;

public interface ClientRepository {
    void addProductToClient(ProductSales newProductSales, String id);
    void addJointAccountToProductToClient(ProductSales newProductSales, String id, JointAccount jointAccount);
    void addJointMovementToProductToClient(ProductSales newProductSales, String id, Movement movement);
}

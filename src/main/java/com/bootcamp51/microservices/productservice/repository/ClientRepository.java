package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface ClientRepository {
    Mono<Client> addProductToClient(ProductSales newProductSales, String id);
    void addJointAccountToProductToClient(ProductSales newProductSales, String id, JointAccount jointAccount);
    Mono<Client> addJointMovementToProductToClient(ProductSales newProductSales, String id, Movement movement);
}

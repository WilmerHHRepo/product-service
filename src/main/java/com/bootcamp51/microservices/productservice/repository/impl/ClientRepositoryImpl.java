package com.bootcamp51.microservices.productservice.repository.impl;

import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final MongoTemplate mongoTemplate;
    private Query query;
    private Update update;

    @Autowired
    public ClientRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void addProductToClient(ProductSales newProductSales, String id) {
        update =  new Update();
        query =  new Query();
        query.addCriteria(Criteria.where("_id").is(id)
        );
        update.push("products",newProductSales);
        mongoTemplate.updateFirst(query, update, Client.class, "client");
    }

    @Override
    public void addJointAccountToProductToClient(ProductSales productSales, String id, JointAccount jointAccount) {
        update =  new Update();
        query =  new Query();
        query.addCriteria(Criteria.where("_id").is(id)
                        .and("products").elemMatch(
                                Criteria.where("numAccount").is(productSales.getNumAccount())
                                        .and("indProduct").is(productSales.getIndProduct())
                                        .and("indTypeProduct").is(productSales.getIndTypeProduct())
                )
        );
        update.push("products.$.jointAccount",jointAccount);
        mongoTemplate.updateFirst(query, update, Client.class, "client");
    }

    @Override
    public void addJointMovementToProductToClient(ProductSales productSales, String id, Movement movement) {
        update =  new Update();
        query =  new Query();
        query.addCriteria(Criteria.where("_id").is(id)
                .and("products").elemMatch(
                        Criteria.where("numAccount").is(productSales.getNumAccount())
                                .and("indProduct").is(productSales.getIndProduct())
                                .and("indTypeProduct").is(productSales.getIndTypeProduct())
                )
        );
        update.push("products.$.movements",movement)
                .set("products.countableBalance", productSales.getCountableBalance())
                .set("products.availableBalance", productSales.getAvailableBalance());
        mongoTemplate.updateFirst(query, update, Client.class, "client");
    }

}

package com.bootcamp51.microservices.productservice.repository.impl;

import com.bootcamp51.microservices.productservice.constant.ConstantGeneral;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ClientRepositoryImpl implements ClientRepository  {

    private final  ReactiveMongoTemplate mongoTemplate;
    private final  ReactiveMongoTemplate mongoTemplate1;
    private Query query;
    private Update update;

    public ClientRepositoryImpl(ReactiveMongoTemplate mongoTemplate, ReactiveMongoTemplate mongoTemplate1) {
        this.mongoTemplate = mongoTemplate;
        this.mongoTemplate1 = mongoTemplate1;
    }

    @Override
    public Mono<Client> addProductToClient(ProductSales newProductSales, String id) {
        update =  new Update();
        query =  new Query();
        query.addCriteria(Criteria.where("_id").is(id)
        );
        update.push("products",newProductSales);
        return mongoTemplate1.findAndModify(query, update, Client.class, ConstantGeneral.COLLECTION_CLIENT);
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
        mongoTemplate.findAndModify(query, update, Client.class, ConstantGeneral.COLLECTION_CLIENT);
    }

    @Override
    public Mono<Client> addJointMovementToProductToClient(ProductSales productSales, String id, Movement movement) {
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
                .set("products.$.countableBalance", productSales.getCountableBalance())
                .set("products.$.availableBalance", productSales.getAvailableBalance());
        return mongoTemplate1.findAndModify(query, update, Client.class, ConstantGeneral.COLLECTION_CLIENT);
    }
}

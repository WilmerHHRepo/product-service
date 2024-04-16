package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Commission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface CommissionRepository extends ReactiveMongoRepository<Commission, Object> {

}

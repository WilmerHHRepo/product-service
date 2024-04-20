package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Commission;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CommissionRepository extends ReactiveMongoRepository<Commission, Object> {

}

package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface ParameterRepository extends ReactiveMongoRepository<Parameter, ObjectId> {
    @Query("{'codParameter': ?0 }")
    Mono<Parameter> findByCodParameter(String codParameter);
}

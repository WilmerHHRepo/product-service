package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends MongoRepository<Parameter, ObjectId> {
    @Query("{'codParameter': ?0 }")
    Parameter findByCodParameter(String codParameter);
}

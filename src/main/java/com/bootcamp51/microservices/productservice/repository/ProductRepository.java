package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductRepository extends ReactiveMongoRepository<Product, Object> {
    @Query("{'desProduct': {$regex: ?0 } }")
    Flux<List<Product>> findByDesProduct(String desProduct);

    @Query("{'_id': ObjectId(?0) }")
    Mono<Product> findById(String id);
}


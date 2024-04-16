package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import java.util.List;

public interface ProductRepository extends ReactiveMongoRepository<Product, Object> {
    @Query("{'desProduct': {$regex: ?0 } }")
    List<Product> findByDesProduct(String desProduct);

    @Query("{'_id': ObjectId(?0) }")
    Product findById(String id);
}


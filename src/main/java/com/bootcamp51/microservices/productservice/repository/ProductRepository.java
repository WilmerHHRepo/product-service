package com.bootcamp51.microservices.productservice.repository;

import com.bootcamp51.microservices.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Object> {
    @Query("{'desProduct': {$regex: ?0 } }")
    List<Product> findByDesProduct(String desProduct);

    @Query("{'_id': ObjectId(?0) }")
    Product findById(String id);
}

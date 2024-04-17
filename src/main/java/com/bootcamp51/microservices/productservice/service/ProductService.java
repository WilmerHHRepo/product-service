package com.bootcamp51.microservices.productservice.service;


import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class ProductService.
 */
public interface ProductService {

    Flux<Product> findAllProduct();

    Flux<List<Product>> findByDesProduct(String desProduct);

    Mono<Product> findById(String id);

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Product product);

    Mono<Product> deleteProduct(String id);



}

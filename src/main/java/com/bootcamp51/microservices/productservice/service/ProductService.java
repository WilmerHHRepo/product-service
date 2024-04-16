package com.bootcamp51.microservices.productservice.service;


import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class ProductService.
 */
@FunctionalInterface
public interface ProductService {

    Flux<Product> findAllProduct();

    default List<Product> findByDesProduct(ProductRepository productRepository, String desProduct){
        Function<String, List<Product>> function = productRepository::findByDesProduct;
        return function.apply(desProduct);
    }

    default Product findById(ProductRepository productRepository, String id){
        Function<String, Product> function = productRepository::findById;
        return function.apply(id);
    }

    default Product createProduct(ProductRepository productRepository, Product product){
        Supplier<Product> supplier = () -> productRepository.save(product);
        return supplier.get();
    }

    default Product updateProduct(ProductRepository productRepository, Product product){
        Supplier<Product> supplier = () -> productRepository.save(product);
        return supplier.get();
    }

    default Product deleteProduct(ProductRepository productRepository, String id){
        ObjectId objectId = new ObjectId(id);
        Product product =  findById(productRepository, id);
        Consumer<ObjectId> consumer = productRepository::deleteById;
        consumer.accept(objectId);
        return product;
    }



}

package com.bootcamp51.microservices.productservice.service.impl;

import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import com.bootcamp51.microservices.productservice.service.ProductService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class ProductServiceImpl.
 */
@Service
public class ProductServiceImpl implements ProductService  {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Flux<Product> findAllProduct() {
    return productRepository.findAll();
  }

  @Override
  public Flux<List<Product>> findByDesProduct(String desProduct){
    return productRepository.findByDesProduct(desProduct);
  }

  @Override
  public Mono<Product> findById(String id){
    //Function<String, Product> function = productRepository::findById;
    return productRepository.findById(id);
  }

  @Override
  public Mono<Product> createProduct(Product product){
    //Supplier<Product> supplier = () -> productRepository.save(product);
    return productRepository.save(product);
  }

  @Override
  public Mono<Product> updateProduct(Product product){
    return productRepository.save(product);
  }

  @Override
  public Mono<Product> deleteProduct(String id){
    ObjectId objectId = new ObjectId(id);
    Mono<Product> product =  findById(id);
    Consumer<ObjectId> consumer = productRepository::deleteById;
    consumer.accept(objectId);
    return product;
  }

}

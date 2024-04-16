package com.bootcamp51.microservices.productservice.service.impl;

import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import com.bootcamp51.microservices.productservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Class ProductServiceImpl.
 */
@Service
public class ProductServiceImpl implements ProductService  {
  @Autowired
  private ProductRepository productRepository;

  @Override
  public Flux<Product> findAllProduct() {
    return productRepository.findAll();
  }
}

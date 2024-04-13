package com.bootcamp51.microservices.productservice.service.impl;

import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import com.bootcamp51.microservices.productservice.service.ProductService;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class ProductServiceImpl.
 */
@Service
public class ProductServiceImpl implements ProductService  {
  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<Product> findAllProduct() {
    Supplier<List<Product>> supplier = productRepository::findAll;
    return supplier.get();
  }
}

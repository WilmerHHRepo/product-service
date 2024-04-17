package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import com.bootcamp51.microservices.productservice.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/bootcamp51/ms/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> findAllProduct() {
        //List<Product> lstProduct = productService.findAllProduct();
        return productService.findAllProduct();
    }

    @GetMapping("/by-product/{product}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<List<Product>> findByDesProduct(@PathVariable String product) {
        //List<Product> lstProduct = productService.findByDesProduct(product);
        return productService.findByDesProduct(product);
    }


    @GetMapping("/by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> findById(@PathVariable String id) {
        //Product product = productService.findById(id);
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        //product = productService.createProduct(product);
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public Mono<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        ObjectId objectId = new ObjectId(id);
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Product> deleteProduct(@PathVariable String id) {
        //Product product = productService.deleteProduct(productRepository, id);
        return productService.deleteProduct(id);
    }
}

package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.model.Product;
import com.bootcamp51.microservices.productservice.repository.ProductRepository;
import com.bootcamp51.microservices.productservice.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/bootcamp51/ms/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> findAllclient() {
        List<Product> lstProduct = productService.findAllProduct();
        return new ResponseEntity<>(lstProduct, HttpStatus.OK);
    }

    @GetMapping("/by-product/{product}")
    public ResponseEntity<List<Product>> findByDesProduct(@PathVariable String product) {
        List<Product> lstProduct = productService.findByDesProduct(productRepository, product);
        return new ResponseEntity<>(lstProduct, HttpStatus.OK);
    }


    @GetMapping("/by-id/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Product product = productService.findById(productRepository, id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product = productService.createProduct(productRepository, product);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        ObjectId objectId = new ObjectId(id);
        product.setId(objectId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        Product product = productService.deleteProduct(productRepository, id);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }
}

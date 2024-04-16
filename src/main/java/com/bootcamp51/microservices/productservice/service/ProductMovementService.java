package com.bootcamp51.microservices.productservice.service;

public interface ProductMovementService<T, A, B> {
    T execute(A a, B b);
}

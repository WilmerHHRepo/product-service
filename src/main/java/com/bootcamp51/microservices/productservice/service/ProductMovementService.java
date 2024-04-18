package com.bootcamp51.microservices.productservice.service;

public interface ProductMovementService<T> {
    T execute(T t);
}

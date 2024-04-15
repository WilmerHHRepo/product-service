package com.bootcamp51.microservices.productservice.service;

public interface ProductMovementService<A, B> {
    A execute(A a, B b);
}

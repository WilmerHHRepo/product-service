package com.bootcamp51.microservices.productservice.service;

@FunctionalInterface
public interface ProductSalesService<A, B, C> {
    A execute(B t, C c);
}

package com.bootcamp51.microservices.productservice.model.vo;

import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientProductSalesRequest {
    private Client client;
    private ProductSales newProductSales;
}

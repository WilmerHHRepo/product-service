package com.bootcamp51.microservices.productservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleSales {
    private String indTypeClient;
    private String indProduct;
    private String number;
}

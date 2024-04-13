package com.bootcamp51.microservices.productservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleMovement {
    private String indTypeClient;
    private String indProduct;
    private String Commission;
    private String indRule;
    private String desRule;
    private Movement movement;
}

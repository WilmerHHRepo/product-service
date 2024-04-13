package com.bootcamp51.microservices.productservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListParameter {
    private List<RuleMovement> listRuleMovement;
    private List<RuleSales> listRuleSales;
}

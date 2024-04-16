package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commission extends Product {
    @Id
    private String id;
    private String indTypeMovement;
    private String desTypeMovement;
    private String numAccount;
    private BigDecimal availableBalance;
    private BigDecimal relativeAmount;
    private BigDecimal operationAmount;
    private BigDecimal commission;
    private Date resgistrationDate;
}

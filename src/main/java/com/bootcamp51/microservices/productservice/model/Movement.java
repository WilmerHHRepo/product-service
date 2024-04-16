package com.bootcamp51.microservices.productservice.model;

import com.bootcamp51.microservices.productservice.constant.ConstantGeneral;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movement {
    private String indTypeMovement;
    private String desTypeMovement;
    private BigDecimal operationAmount;
    private String establishment;
    private Integer totalFees;
    private Integer pendingInstallments;
    private Integer duesPaid;
    private String indPurchaseOrigin;
    private String desPurchaseOrigin;
    private Date resgistrationDate;
}

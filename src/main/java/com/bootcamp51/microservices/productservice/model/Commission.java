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
    /**
     * value numOperation.
     */
    private String numOperation;
    /**
     * value indTypeDocument.
     */
    private String indTypeDocument;
    /**
     * value desTypeDocument.
     */
    private String desTypeDocument;
    /**
     * value numDocument.
     */
    private String numDocument;
    /**
     * value indTypeMovement.
     */
    private String indTypeMovement;
    /**
     * value desTypeMovement.
     */
    private String desTypeMovement;
    /**
     * value numAccount.
     */
    private String numAccount;
    /**
     * value availableBalance.
     */
    private BigDecimal availableBalance;
    /**
     * value relativeAmount.
     */
    private BigDecimal relativeAmount;
    /**
     * value operationAmount.
     */
    private BigDecimal operationAmount;
    /**
     * value commission.
     */
    private BigDecimal commission;
    /**
     * value registrationDate.
     */
    private Date registrationDate;
}

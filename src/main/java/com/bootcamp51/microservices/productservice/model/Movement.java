package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Class Movement model.
 * author by Wilmer H.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movement {

  /**
   * value numOperation.
   */
  private String numOperation;

  /**
   * value indTypeMovement.
   */
  private String indTypeMovement;

  /**
   * value desTypeMovement.
   */
  private String desTypeMovement;

  /**
   * value originAccount.
   */
  private String originAccount;

  /**
   * value destinationAccount.
   */
  private String destinationAccount;

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
   * value establishment.
   */
  private String establishment;

  /**
   * value totalFees.
   */
  private Integer totalFees;

  /**
   * value pendingInstallments.
   */
  private Integer pendingInstallments;

  /**
   * value duesPaid.
   */
  private Integer duesPaid;

  /**
   * value indPurchaseOrigin.
   */
  private String indPurchaseOrigin;

  /**
   * value desPurchaseOrigin.
   */
  private String desPurchaseOrigin;

  /**
   * value resgistrationDate.
   */
  private Date resgistrationDate;
}

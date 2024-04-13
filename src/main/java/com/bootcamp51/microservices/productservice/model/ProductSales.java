package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


/**
 * Class ProductSales model.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSales extends Product {
  /**
   * value numAccount.
   */
  private String numAccount;
  /**
   * value desCCI.
   */
  private String desCodeInterbank;
  /**
   * value numAccountCCI.
   */
  private String numAccountCodeInterbank;
  /**
   * value countableBalance.
   */
  private BigDecimal countableBalance;
  /**
   * value availableBalance.
   */
  private BigDecimal availableBalance;
  /**
   * value totalFees.
   */
  private Integer totalFees;
  /**
   * value jointAccount.
   */
  private JointAccount jointAccount;
  /**
   * value movements.
   */
  private Movement movements;
}

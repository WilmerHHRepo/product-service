package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Class JointAccount model.
 * author by Wilmer H.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JointAccount {
  /**
   * value members.
   */
  private String[] members;
  /**
   * value indProduct.
   */
  private String indProduct;
  /**
   * value desProduct.
   */
  private String desProduct;
  /**
   * value numAccount.
   */
  private String numAccount;
  /**
   * value ownerCode.
   */
  private String ownerCode;
  /**
   * value signerCode.
   */
  private String signerCode;
}

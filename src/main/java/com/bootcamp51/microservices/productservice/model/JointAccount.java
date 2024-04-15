package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Class JointAccount model.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JointAccount {
  /**
   * valor members.
   */
  private String[] members;
  /**
   * valor indProduct.
   */
  private String indProduct;
  /**
   * valor desProduct.
   */
  private String desProduct;
  /**
   * valor numAccount.
   */
  private String numAccount;
  /**
   * valor ownerCode.
   */
  private String ownerCode;
  /**
   * valor signerCode.
   */
  private String signerCode;
}

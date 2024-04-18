package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Class Product model.
 * author by Wilmer H.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

  /**
   * value id.
   */
  @Id
  private String id;

  /**
   * value indTypeProduct.
   */
  private String indTypeProduct;

  /**
   * value desTypeProduct.
   */
  private String desTypeProduct;

  /**
   * value indProduct.
   */
  private String indProduct;

  /**
   * value desProduct.
   */
  private String desProduct;
}

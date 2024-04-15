package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Class Product model.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
  /**
   * valor id.
   */
  @Id
  private String id;
  /**
   * valor indTypeProduct.
   */
  private String indTypeProduct;
  /**
   * valor desTypeProduct.
   */
  private String desTypeProduct;
  /**
   * valor indProduct.
   */
  private String indProduct;
  /**
   * valor desProduct.
   */
  private String desProduct;
}

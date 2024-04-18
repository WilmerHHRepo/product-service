package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Class Client model.
 * author by Wilmer H.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

  /**
   * value id.
   */
  @Id
  private String id;

  /**
   * value indTypeClient.
   */
  private String indTypeClient;

  /**
   * value desTypeClient.
   */
  private String desTypeClient;

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
   * value name.
   */
  private String name;

  /**
   * value lastName.
   */
  private String lastName;

  /**
   * value products.
   */
  private List<ProductSales> products;


}

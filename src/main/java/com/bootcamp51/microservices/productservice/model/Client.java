package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


/**
 * Class Client model.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
  /**
   * valor id.
   */
  @Id
  private String id;
  /**
   * valor indTypeClient.
   */
  private String indTypeClient;
  /**
   * valor desTypeClient.
   */
  private String desTypeClient;
  /**
   * valor indTypeDocument.
   */
  private String indTypeDocument;
  /**
   * valor desTypeDocument.
   */
  private String desTypeDocument;
  /**
   * valor numDocument.
   */
  private String numDocument;
  /**
   * valor name.
   */
  private String name;
  /**
  * valor lastName.
   */
  private String lastName;
  /**
   * valor products.
   */
  private List<ProductSales> products;


}

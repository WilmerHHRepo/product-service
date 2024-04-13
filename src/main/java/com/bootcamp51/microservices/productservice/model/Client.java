package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;


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
  private ObjectId id;
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

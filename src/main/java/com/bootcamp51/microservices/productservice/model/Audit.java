package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Class Audit model.
 * author by Wilmer H.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Audit {

  /**
   * value userRegister.
   */
  private String userRegister;

  /**
   * value restrationDate.
   */
  private Date restrationDate;

  /**
   * value userModification.
   */
  private String userModification;

  /**
   * value ModificationDate.
   */
  private Date modificationDate;
}

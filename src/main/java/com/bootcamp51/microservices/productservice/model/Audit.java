package com.bootcamp51.microservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Audit {
    private String userRegister;
    private Date restrationDate;
    private String userModification;
    private Date ModificationDate;
}

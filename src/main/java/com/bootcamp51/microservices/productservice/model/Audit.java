package com.bootcamp51.microservices.productservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Audit {
    private String userRegister;
    private Date restrationDate;
    private String userModification;
    private Date ModificationDate;
}

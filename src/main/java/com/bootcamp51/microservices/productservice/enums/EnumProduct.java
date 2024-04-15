package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumProduct {

    SAVINGSACCOUNT("1","Savings Account"),
    CURRENTACCOUNT("2","Current Account"),
    FIXEDTERMACCOUNT("3","Fixed Term Account"),
    PERSONALCREDIT("4","Personal Credit"),
    COMMERCIALCREDIT("5","Commercial Credit"),
    CREDITCARD("6","Credit Card");
    private String code;
    private String description;

    EnumProduct(String code, String description) {
        this.code = code;
        this.description = description;
    }

}

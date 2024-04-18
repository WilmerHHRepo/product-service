package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumProduct {

    SAVINGS_ACCOUNT("1","Savings Account"),
    CURRENT_ACCOUNT("2","Current Account"),
    FIXED_TERM_ACCOUNT("3","Fixed Term Account"),
    PERSONAL_CREDIT("4","Personal Credit"),
    COMMERCIAL_CREDIT("5","Commercial Credit"),
    CREDIT_CARD("6","Credit Card");
    private String code;
    private String description;

    EnumProduct(String code, String description) {
        this.code = code;
        this.description = description;
    }

}

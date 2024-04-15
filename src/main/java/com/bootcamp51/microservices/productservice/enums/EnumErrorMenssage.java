package com.bootcamp51.microservices.productservice.enums;


import lombok.Getter;

@Getter
public enum EnumErrorMenssage {
    ERROR1001("1001","Exceeds the number of accounts per customer."),
    ERROR1002("1002","Product without sale."),
    ERROR1003("1003","Exceeds the number of deposit."),
    ERROR1004("1004","Exceeds the number of retreat."),
    ERROR1005("1005","insufficient balance.")
    ;

    private String code;
    private String description;

    EnumErrorMenssage(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
package com.bootcamp51.microservices.productservice.enums;


import lombok.Getter;

@Getter
public enum EnumErrorMenssage {
    ERROR1001("1001","Exceeds the number of accounts per customer."),
    ERROR1002("1002","Product without sale."),
    ERROR1003("1003","Exceeds the number of deposit."),
    ERROR1004("1004","Exceeds the number of retreat."),
    ERROR1005("1005","insufficient balance."),
    ERROR1006("1006","Product does not exist."),
    ERROR1007("1007","Destination account does not exist."),
    ERROR1008("1008","The type client requires a minimum of a savings account and a credit card.")
    ;

    private String code;
    private String description;

    EnumErrorMenssage(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

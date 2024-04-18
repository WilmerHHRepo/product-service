package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumTypeMovement {
    DEPOSIT("1","Deposit"),
    RETREAT("2","Retreat"),
    PAYMENT("3","Payment"),
    CONSUMPTION("4","Consumption"),
    TRANSFER_BETWEEN_CLIENT_ACCOUNTS("5","Transfer between client accounts"),
    TRANSFER_BETWEEN_ACCOUNTS_THIRD_PARTIES("6","Transfer between accounts to third parties")

    ;
    private String code;
    private String description;
    EnumTypeMovement(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

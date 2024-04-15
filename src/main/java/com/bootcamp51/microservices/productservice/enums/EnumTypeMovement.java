package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumTypeMovement {
    DEPOSITO("1","Savings Account"),
    RETIRO("2","Current Account"),
    PAGO("3","Fixed Term Account"),
    CONSUMO("4","Personal Credit");
    private String code;
    private String description;
    EnumTypeMovement(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

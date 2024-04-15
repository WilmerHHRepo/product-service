package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumTypeDocument {

    DNI("1","DNI", ""),
    RUC("2","RUC", "");
    private String code;
    private String descriptionSmall;
    private String descriptionLong;

    EnumTypeDocument(String code, String descriptionSmall, String descriptionLong) {
        this.code = code;
        this.descriptionSmall = descriptionSmall;
        this.descriptionLong = descriptionLong;
    }
}

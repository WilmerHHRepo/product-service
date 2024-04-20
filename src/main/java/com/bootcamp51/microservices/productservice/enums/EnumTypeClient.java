package com.bootcamp51.microservices.productservice.enums;

import lombok.Getter;

@Getter
public enum EnumTypeClient  {
    PERSON("1","Person"),
    COMPANY("2","Company"),
    PERSON_VIP("2","Company"),
    COMPANY_PYME("2","Company");
    private String code;
    private String description;

    EnumTypeClient(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

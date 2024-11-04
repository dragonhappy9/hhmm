package com.example.hhmm.Customer;

import lombok.Getter;

@Getter
public enum CustomerRole {
    MARKETMANAGER("ROLE_MM"),
    POSTMANAGER("ROLE_PM"),
    CUSTOMER("ROLE_CUSTOMER");

    private String value;

    CustomerRole(String value){
        this.value = value;
    }
}

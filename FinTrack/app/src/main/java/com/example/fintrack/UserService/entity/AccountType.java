package com.example.fintrack.UserService.entity;

public class AccountType {

    private String typeId;     // WALLET, BANK, CREDIT
    private String name;
    private String description;

    public AccountType(String typeId, String name, String description) {
        this.typeId = typeId;
        this.name = name;
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }
}

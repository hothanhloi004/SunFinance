package com.example.fintrack.UserService.entity;

public class Account {

    private String accountId;
    private String userId;
    private String name;
    private AccountType type;
    private double balance;
    private String status;

    public Account(String accountId, String userId, String name,
                   AccountType type, double balance, String status) {
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }
}

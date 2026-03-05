package com.example.fintrack.AccountService.model;

public class AccountDTO {
    public String id;
    public String name;
    public double currentBalance;
    public static AccountDTO fromEntity(AccountEntity entity) {
        AccountDTO dto = new AccountDTO();
        dto.id = entity.accountId;
        dto.name = entity.name;
        dto.currentBalance = entity.balance;
        return dto;
    }
}
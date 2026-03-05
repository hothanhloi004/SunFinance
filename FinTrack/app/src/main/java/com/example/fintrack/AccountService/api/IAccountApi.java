package com.example.fintrack.AccountService.api;

public interface IAccountApi {
    void deleteOrHideAccount(String id);
    void updateBalance(String accountId, double amount);
}
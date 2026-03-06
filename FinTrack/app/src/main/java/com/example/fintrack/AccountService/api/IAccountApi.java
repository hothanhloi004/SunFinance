package com.example.fintrack.AccountService.api;

public interface IAccountApi {

    double getBalance(String accountId);

    void updateBalance(String accountId, double amount);

    void deleteOrHideAccount(String id);
}
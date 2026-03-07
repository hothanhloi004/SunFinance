package com.example.fintrack.AccountService.api;

import java.util.List;
import com.example.fintrack.AccountService.model.AccountEntity;
public interface IAccountApi {

    double getBalance(String accountId);

    void updateBalance(String accountId, double amount);

    void deleteOrHideAccount(String id);

    List<AccountEntity> getAccountsByUser(String userId);
}
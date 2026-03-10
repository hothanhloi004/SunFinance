package com.example.fintrack.AccountService.port;

import java.util.List;
import com.example.fintrack.AccountService.model.AccountEntity;
public interface AccountPort {

    double getBalance(String accountId);

    void updateBalance(String accountId, double amount);

    void deleteOrHideAccount(String id);

    List<AccountEntity> getAccountsByUser(String userId);
}
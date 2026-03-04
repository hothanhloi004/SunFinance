package com.example.fintrack.TransactionService.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.fintrack.TransactionService.data.entity.AccountEntity;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM accounts WHERE account_id = :accountId LIMIT 1")
    AccountEntity getById(String accountId);

    @Query("UPDATE accounts SET balance = balance + :amount WHERE account_id = :accountId")
    void updateBalance(String accountId, double amount);

    @Query("SELECT * FROM accounts WHERE user_id = :userId")
    List<AccountEntity> getAccountsByUser(String userId);
}
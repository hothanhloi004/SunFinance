package com.example.fintrack.AccountService.usecase;

import android.content.Context;

import com.example.fintrack.TransactionService.data.dao.AccountDao;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.AccountEntity;

public class GetAccountBalanceUseCase {

    private final AccountDao accountDao;

    public GetAccountBalanceUseCase(Context context) {
        accountDao = FintrackDatabase.getInstance(context).accountDao();
    }

    public double execute(String accountId) {

        AccountEntity acc = accountDao.getById(accountId);

        if (acc == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return acc.balance;
    }
}
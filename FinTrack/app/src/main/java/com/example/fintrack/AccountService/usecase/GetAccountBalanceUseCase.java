package com.example.fintrack.AccountService.usecase;

import android.content.Context;

import com.example.fintrack.AccountService.data.AccountDao;
import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;

public class GetAccountBalanceUseCase {

    private final AccountDao accountDao;

    public GetAccountBalanceUseCase(Context context) {
        accountDao = FintrackDatabase
                .getInstance(context)
                .accountDao();
    }

    public double execute(String accountId) {

        AccountEntity acc = accountDao.getById(accountId);

        if (acc == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return acc.balance;
    }
}
package com.example.fintrack.AccountService.usecase;

import android.content.Context;

import com.example.fintrack.TransactionService.data.dao.AccountDao;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;

public class UpdateBalanceUseCase {

    private final AccountDao accountDao;

    public UpdateBalanceUseCase(Context context) {
        accountDao = FintrackDatabase.getInstance(context).accountDao();
    }

    public void execute(String accountId, double amount) {
        accountDao.updateBalance(accountId, amount);
    }

}
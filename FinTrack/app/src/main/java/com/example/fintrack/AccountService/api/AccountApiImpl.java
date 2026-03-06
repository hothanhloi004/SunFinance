package com.example.fintrack.AccountService.api;

import android.content.Context;

import com.example.fintrack.AccountService.usecase.DeleteAccountUseCase;
import com.example.fintrack.AccountService.usecase.UpdateBalanceUseCase;
import com.example.fintrack.AccountService.usecase.GetAccountBalanceUseCase;

public class AccountApiImpl implements IAccountApi {
    private final DeleteAccountUseCase deleteUC = new DeleteAccountUseCase();
    private final UpdateBalanceUseCase updateBalanceUC;
    private final GetAccountBalanceUseCase getBalanceUC;

    public AccountApiImpl(Context context) {
        updateBalanceUC = new UpdateBalanceUseCase(context);
        getBalanceUC = new GetAccountBalanceUseCase(context);
    }

    @Override
    public void deleteOrHideAccount(String id) {
        deleteUC.execute(id);
    }

    @Override
    public void updateBalance(String accountId, double amount) {
        updateBalanceUC.execute(accountId, amount);
    }

    @Override
    public double getBalance(String accountId) {
        return getBalanceUC.execute(accountId);
    }

}

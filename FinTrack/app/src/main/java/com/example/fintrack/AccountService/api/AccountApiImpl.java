package com.example.fintrack.AccountService.api;

import com.example.fintrack.AccountService.usecase.DeleteAccountUseCase;
import com.example.fintrack.AccountService.usecase.UpdateBalanceUseCase;

public class AccountApiImpl implements IAccountApi {
    private final DeleteAccountUseCase deleteUC = new DeleteAccountUseCase();
    private final UpdateBalanceUseCase updateBalanceUC = new UpdateBalanceUseCase();

    @Override
    public void deleteOrHideAccount(String id) {
        deleteUC.execute(id);
    }

    @Override
    public void updateBalance(String accountId, double amount) {
        updateBalanceUC.execute(accountId, amount);
    }
}
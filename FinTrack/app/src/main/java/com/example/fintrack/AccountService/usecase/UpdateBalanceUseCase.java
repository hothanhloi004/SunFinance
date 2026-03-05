package com.example.fintrack.AccountService.usecase;

import com.example.fintrack.AccountService.data.AccountRepository;
import com.example.fintrack.AccountService.model.AccountEntity;

public class UpdateBalanceUseCase {
    private AccountRepository repo = AccountRepository.getInstance(); // Sửa chỗ này

    public void execute(String accountId, double amount) {
        AccountEntity acc = repo.getAccountById(accountId); // Tối ưu vòng lặp
        if (acc != null) {
            acc.balance += amount;
            repo.updateAccount(acc);
        }
    }
}
package com.example.fintrack.AccountService.usecase;

import com.example.fintrack.AccountService.data.AccountRepository;
import com.example.fintrack.AccountService.model.AccountEntity;

public class DeleteAccountUseCase {
    private AccountRepository repo = AccountRepository.getInstance(); // Sửa chỗ này

    public void execute(String accountId) {
        boolean hasTransactions = repo.hasTransactions(accountId);
        if (!hasTransactions) {
            repo.deleteAccount(accountId);
        } else {
            repo.updateStatus(accountId, AccountEntity.STATUS_HIDDEN); // Dùng hằng số
        }
    }
}
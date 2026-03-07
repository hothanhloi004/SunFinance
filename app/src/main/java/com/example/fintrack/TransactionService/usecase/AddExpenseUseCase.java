package com.example.fintrack.TransactionService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.usecase.UpdateSpentUseCase;
import com.example.fintrack.TransactionService.data.TransactionRepository;
import com.example.fintrack.TransactionService.entity.Transaction;
import com.example.fintrack.AlertService.util.IdGenerator;

public class AddExpenseUseCase {

    private final TransactionRepository repo;
    private final UpdateSpentUseCase updateSpent;

    public AddExpenseUseCase(TransactionRepository repo,
                             UpdateSpentUseCase updateSpent) {

        this.repo = repo;
        this.updateSpent = updateSpent;
    }

    public void execute(Context context,
                        String category,
                        double amount){

        Transaction t = new Transaction(
                IdGenerator.generate(),
                category,
                amount,
                "EXPENSE",
                System.currentTimeMillis()
        );

        repo.insert(t);

        updateSpent.execute(context, category, amount);
    }
}
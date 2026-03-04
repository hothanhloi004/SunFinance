package com.example.fintrack.TransactionService.usecase;

import android.content.Context;

import com.example.fintrack.TransactionService.entity.TransactionEntity;
import com.example.fintrack.TransactionService.service.TransactionService;

public class CreateTransactionUseCase {

    private final TransactionService service;

    public CreateTransactionUseCase(Context context) {
        service = new TransactionService(context);
    }

    public void execute(TransactionEntity transaction) {
        service.save(transaction);
    }
}
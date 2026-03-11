package com.example.fintrack.AlertService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.AlertService.service.AlertDomainService;
import com.example.fintrack.NotificationService.service.NotificationHelper;

import java.util.List;

import com.example.fintrack.TransactionService.port.TransactionPort;
import com.example.fintrack.TransactionService.api.TransactionApiImpl;

public class UpdateSpentUseCase {

    private final AlertRepository repo;
    private final TransactionPort transactionPort;

    public UpdateSpentUseCase(Context context, AlertRepository repo) {
        this.repo = repo;
        this.transactionPort = new TransactionApiImpl(context);
    }

    public void execute(Context context, String userId, String categoryId, String month) {

        List<BudgetAlert> list = repo.findAll();
        AlertDomainService domain = new AlertDomainService();

        for (BudgetAlert alert : list) {

            if (!alert.categoryId.equals(categoryId))
                continue;

            // lấy dữ liệu thật từ TransactionService
            Double spent = transactionPort.getTotalExpenseByCategory(
                    userId,
                    categoryId,
                    month
            );

            alert.spent = spent == null ? 0 : spent;

            if (domain.isWarning(alert)) {

                NotificationHelper.send(
                        context,
                        "⚠ Budget 80% reached for " + alert.categoryName
                );
            }

            if (domain.isExceeded(alert)) {

                NotificationHelper.send(
                        context,
                        "🚨 Budget exceeded for " + alert.categoryName
                );
            }
        }

    }

}
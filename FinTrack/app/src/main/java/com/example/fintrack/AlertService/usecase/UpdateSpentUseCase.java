package com.example.fintrack.AlertService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.AlertService.service.AlertDomainService;
import com.example.fintrack.NotificationService.service.NotificationHelper;

import java.util.List;

public class UpdateSpentUseCase {

    private final AlertRepository repo;

    public UpdateSpentUseCase(AlertRepository repo) {
        this.repo = repo;
    }

    public void execute(Context context, String categoryId, double amount) {

        List<BudgetAlert> list = repo.findAll();

        AlertDomainService domain = new AlertDomainService();

        for (BudgetAlert alert : list) {

            if (!alert.category.equals(categoryId))
                continue;

            // cập nhật spent
            alert.spent += amount;

            if (domain.isWarning(alert)) {

                NotificationHelper.send(
                        context,
                        "⚠ Budget 80% reached for " + alert.category
                );
            }

            if (domain.isExceeded(alert)) {

                NotificationHelper.send(
                        context,
                        "🚨 Budget exceeded for " + alert.category
                );
            }
        }
    }
}
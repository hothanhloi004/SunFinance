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

    public void execute(Context context,
                        String category,
                        double amount) {

        repo.updateSpent(category, amount);

        List<BudgetAlert> list = repo.findAll();
        AlertDomainService domain =
                new AlertDomainService();

        for (BudgetAlert a : list) {

            if (a.category.equals(category)) {

                if (domain.isWarning(a)) {
                    NotificationHelper.send(context,
                            "⚠ " + category +
                                    " reached 80%");
                }

                if (domain.isExceeded(a)) {
                    NotificationHelper.send(context,
                            "🚨 " + category +
                                    " exceeded limit!");
                }
            }
        }
    }
}
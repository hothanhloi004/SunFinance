package com.example.fintrack.AlertService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.AlertService.service.AlertDomainService;
import com.example.fintrack.NotificationService.service.NotificationHelper;

import java.util.List;

public class CheckBudgetWarningUseCase {

    private final AlertRepository repo;

    public CheckBudgetWarningUseCase(AlertRepository repo) {
        this.repo = repo;
    }

    public void execute(Context context) {

        AlertDomainService domain = new AlertDomainService();
        List<BudgetAlert> list = repo.findAll();

        for (BudgetAlert a : list) {

            if (domain.isWarning(a)) {

                NotificationHelper.send(
                        context,
                        "⚠ " + a.category + " reached 80% of budget"
                );
            }

            if (domain.isExceeded(a)) {

                NotificationHelper.send(
                        context,
                        "🚨 " + a.category + " exceeded budget!"
                );
            }
        }
    }
}
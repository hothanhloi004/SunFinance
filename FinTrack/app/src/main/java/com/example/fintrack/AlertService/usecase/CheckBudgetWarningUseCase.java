package com.example.fintrack.AlertService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.NotificationService.service.NotificationHelper;

import java.util.List;

public class CheckBudgetWarningUseCase {

    private final AlertRepository repo;

    public CheckBudgetWarningUseCase(AlertRepository repo) {
        this.repo = repo;
    }

    public void execute(Context context) {

        List<BudgetAlert> list = repo.findAll();

        for (BudgetAlert a : list) {

            if (a.limitAmount <= 0) continue;

            double percent = a.spent / a.limitAmount;

            // reset trigger nếu user chi xuống lại dưới 80%
            if (percent < a.threshold) {
                a.triggered = false;
            }

            // ⚠ đạt 80%
            if (percent >= a.threshold && !a.triggered) {

                NotificationHelper.send(
                        context,
                        "⚠ " + a.categoryName + " reached 80% of budget"
                );

                a.triggered = true;
            }

            // 🚨 vượt ngân sách
            if (a.spent > a.limitAmount) {

                NotificationHelper.send(
                        context,
                        "🚨 " + a.categoryName + " exceeded budget!"
                );
            }
        }
    }
}
package com.example.fintrack.AlertService.service;

import com.example.fintrack.AlertService.entity.BudgetAlert;

public class AlertDomainService {

    public boolean isWarning(BudgetAlert alert) {

        if (alert == null) return false;
        if (alert.limitAmount <= 0) return false;
        if (alert.spent <= 0) return false;

        double percent = alert.spent / alert.limitAmount;

        return percent >= alert.threshold && percent < 1.0;
    }

    public boolean isExceeded(BudgetAlert alert) {

        if (alert == null) return false;
        if (alert.limitAmount <= 0) return false;
        if (alert.spent <= 0) return false;

        double percent = alert.spent / alert.limitAmount;

        return percent >= 1.0;
    }
}
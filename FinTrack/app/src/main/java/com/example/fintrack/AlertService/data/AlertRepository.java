package com.example.fintrack.AlertService.data;

import com.example.fintrack.AlertService.entity.BudgetAlert;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository {

    private final List<BudgetAlert> alerts;

    public AlertRepository() {
        alerts = new ArrayList<>();
    }

    public List<BudgetAlert> findAll() {
        return alerts;
    }

    public void add(BudgetAlert alert) {
        alerts.add(alert);
    }

    public void update(int index, BudgetAlert alert) {
        if (index >= 0 && index < alerts.size()) {
            alerts.set(index, alert);
        }
    }

    public void delete(BudgetAlert alert) {
        alerts.remove(alert);
    }

    public void clear() {
        alerts.clear();
    }
}
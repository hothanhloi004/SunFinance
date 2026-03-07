package com.example.fintrack.AlertService.usecase;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.AlertService.util.IdGenerator;

public class CreateBudgetUseCase {

    private final AlertRepository repo;

    public CreateBudgetUseCase(AlertRepository repo) {
        this.repo = repo;
    }

    public void execute(String category,
                        double limit,
                        String period) {

        BudgetAlert alert = new BudgetAlert(
                IdGenerator.generate(),
                category,
                limit,
                0.0,      // spent = 0 ban đầu
                period,
                0.8,
                false
        );

        repo.save(alert);
    }
}
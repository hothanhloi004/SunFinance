package com.example.fintrack.AlertService.usecase;

import com.example.fintrack.AlertService.data.AlertRepository;
import com.example.fintrack.AlertService.entity.BudgetAlert;
import com.example.fintrack.AlertService.util.IdGenerator;

public class CreateBudgetUseCase {

    private final AlertRepository repo;

    public CreateBudgetUseCase(AlertRepository repo) {
        this.repo = repo;
    }

    public void execute(String categoryId,
                        String categoryName,
                        double limit,
                        String period) {
        for(BudgetAlert a : repo.findAll()){
            if(a.categoryId.equals(categoryId)){
                a.limitAmount = limit;
                return;
            }
        }

        BudgetAlert alert = new BudgetAlert(
                IdGenerator.generate(),
                categoryId,
                categoryName,
                limit,
                0.0,
                period,
                0.8,
                false
        );

        repo.add(alert);
    }
}
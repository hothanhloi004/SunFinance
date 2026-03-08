package com.example.fintrack.AlertService.entity;

public class BudgetAlert {

    public String id;
    public String category;
    public double limitAmount;
    public double spent;
    public String period; // WEEKLY / MONTHLY
    public double threshold; // ví dụ 0.8
    public boolean triggered;

    public BudgetAlert() {}

    public BudgetAlert(String id, String category,
                       double limitAmount,
                       double spent,
                       String period,
                       double threshold,
                       boolean triggered) {
        this.id = id;
        this.category = category;
        this.limitAmount = limitAmount;
        this.spent = spent;
        this.period = period;
        this.threshold = threshold;
        this.triggered = triggered;
    }
}
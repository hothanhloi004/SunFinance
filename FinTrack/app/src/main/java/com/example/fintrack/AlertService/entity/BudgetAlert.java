package com.example.fintrack.AlertService.entity;

public class BudgetAlert {

    public String id;

    // dùng để so sánh với transactions.category_id
    public String categoryId;

    // dùng hiển thị UI / notification
    public String categoryName;

    public double limitAmount;
    public double spent;

    public String period; // WEEKLY / MONTHLY
    public double threshold; // ví dụ 0.8
    public boolean triggered;

    public BudgetAlert() {}

    public BudgetAlert(String id,
                       String categoryId,
                       String categoryName,
                       double limitAmount,
                       double spent,
                       String period,
                       double threshold,
                       boolean triggered) {

        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.limitAmount = limitAmount;
        this.spent = spent;
        this.period = period;
        this.threshold = threshold;
        this.triggered = triggered;
    }
}
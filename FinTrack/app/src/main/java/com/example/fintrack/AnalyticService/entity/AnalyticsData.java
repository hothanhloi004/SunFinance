package com.example.fintrack.AnalyticService.entity;

public class AnalyticsData {

    private String category;
    private double amount;
    private int percent;

    public AnalyticsData(String category, double amount, int percent) {
        this.category = category;
        this.amount = amount;
        this.percent = percent;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }
}
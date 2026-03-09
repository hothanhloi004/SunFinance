package com.example.fintrack.AlertService.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {

    @PrimaryKey
    @NonNull
    public String id;

    public String title;
    public double amount;
    public int dueDay;
    public String period;
    public boolean enabled;

    public Reminder() {
    }

    public Reminder(String id, String title, double amount, int dueDay, String period, boolean enabled) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.dueDay = dueDay;
        this.period = period;
        this.enabled = enabled;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public int getDay() {
        return dueDay;
    }

    public String getPeriod() {
        return period;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alerts")
public class AlertEntity {

    @PrimaryKey
    @NonNull
    public String alert_id;

    public String user_id;
    public String category_id;

    public double monthly_limit;
    public double current_spent;
    public double threshold;     // ví dụ 0.9
    public boolean is_triggered;
}

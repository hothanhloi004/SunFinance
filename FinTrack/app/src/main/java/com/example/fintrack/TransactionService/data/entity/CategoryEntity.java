package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey
    @NonNull
    public String category_id;

    public String name;

    public String tx_type_id; // INCOME / EXPENSE

    public String icon;

    public String user_id;

    public String parent_category_id;

    @Override
    public String toString() {
        return name == null ? "Không có" : name;
    }
}

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

    // ===== logic từ code của bạn =====
    public static String suggestCategory(String description){

        if(description == null) return "Other";

        description = description.toLowerCase();

        if(description.contains("coffee") || description.contains("cafe"))
            return "Food";

        if(description.contains("grab") || description.contains("taxi"))
            return "Transport";

        if(description.contains("salary"))
            return "Income";

        if(description.contains("shopping"))
            return "Shopping";

        return "Other";
    }

    @Override
    public String toString() {
        return name == null ? "Không có" : name;
    }
}
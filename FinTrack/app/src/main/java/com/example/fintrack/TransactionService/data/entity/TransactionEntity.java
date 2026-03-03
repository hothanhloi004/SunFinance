package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey
    @NonNull
    public String tx_id;

    public String user_id;
    public String tx_type_id;          // INCOME / EXPENSE / TRANSFER

    public String source_account_id;   // null nếu INCOME
    public String target_account_id;   // null nếu EXPENSE

    public String category_id;          // null nếu TRANSFER
    public double amount;

    public String note;
    public String tx_date;              // yyyy-MM-dd
    public String created_at;           // timestamp (string)
    public String month;                // yyyy-MM
}

package com.example.fintrack.TransactionService.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey
    @NonNull
    public String tx_id;

    public String user_id;
    public String tx_type_id;
    public String source_account_id;
    public String category_id;

    public double amount;
    public String note;
    public String tx_date;
    public long created_at;
}
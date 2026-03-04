package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class AccountEntity {

    @PrimaryKey
    @NonNull
    public String account_id;

    public String user_id;
    public String name;
    public String type_id;     // WALLET / BANK / CREDIT
    public double balance;
    public String status;
    public String created_at;  // timestamp
}

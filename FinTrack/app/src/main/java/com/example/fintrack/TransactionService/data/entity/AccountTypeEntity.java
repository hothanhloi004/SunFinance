package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_types")
public class AccountTypeEntity {

    @PrimaryKey
    @NonNull
    public String type_id;

    public String name;
    public String description;
}

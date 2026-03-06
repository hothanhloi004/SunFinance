package com.example.fintrack.AccountService.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_types")
public class AccountTypeEntity {

    @PrimaryKey
    @NonNull
    public String typeid;

    public String name;
    public String description;

    public AccountTypeEntity(@NonNull String typeid, String name, String description) {
        this.typeid = typeid;
        this.name = name;
        this.description = description;
    }
}
package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String user_id;

    public String email;
    public String full_name;
    public String status;
    public String created_at;
}

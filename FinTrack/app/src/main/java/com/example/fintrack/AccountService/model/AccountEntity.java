package com.example.fintrack.AccountService.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts") // Giữ lại cho Room sau này
public class AccountEntity {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_HIDDEN = "HIDDEN";
    public static final String TYPE_WALLET = "WALLET";
    public static final String TYPE_BANK = "BANK";

    @PrimaryKey
    @NonNull
    public String accountId;
    public String userId;
    public String name;
    public String typeId;
    public double balance;
    public String status;
    public String createdAt;

    public AccountEntity() {
    }

    public AccountEntity(@NonNull String accountId, String userId, String name, String typeId, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.typeId = typeId;
        this.balance = balance;
        this.status = STATUS_ACTIVE;
    }
}
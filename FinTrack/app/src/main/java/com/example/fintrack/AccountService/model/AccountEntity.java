package com.example.fintrack.AccountService.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class AccountEntity {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_HIDDEN = "HIDDEN";
    public static final String TYPE_WALLET = "WALLET";
    public static final String TYPE_BANK = "BANK";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "account_id")
    public String accountId;

    @ColumnInfo(name = "user_id")
    public String userId;

    public String name;

    @ColumnInfo(name = "type_id")
    public String typeId;

    public double balance;

    public String status;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    public AccountEntity() {}

    public AccountEntity(@NonNull String accountId, String userId, String name, String typeId, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.typeId = typeId;
        this.balance = balance;
        this.status = STATUS_ACTIVE;
    }
}
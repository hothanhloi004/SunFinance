package com.example.fintrack.TransactionService.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tx_types")
public class TxTypeEntity {

    @PrimaryKey
    @NonNull
    public String tx_type_id;   // INCOME / EXPENSE / TRANSFER

    public String name;
    public int sign;            // 1 / -1 / 0
}

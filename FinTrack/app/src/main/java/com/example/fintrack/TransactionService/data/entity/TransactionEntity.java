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

    public String category_id;         // null nếu TRANSFER
    public double amount;

    public String note;

    public String tx_date;             // yyyy-MM-dd
    public String time;                // thêm từ code của bạn

    public String created_at;          // timestamp (string)
    public String month;               // yyyy-MM


    // Constructor rỗng cho Room
    public TransactionEntity() {}


    // Constructor giống code của bạn (dùng khi import CSV)
    public TransactionEntity(String tx_date, String time, double amount, String note) {
        this.tx_date = tx_date;
        this.time = time;
        this.amount = amount;
        this.note = note;
    }


    // ===== Getter =====

    public String getTx_id() {
        return tx_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTx_type_id() {
        return tx_type_id;
    }

    public String getSource_account_id() {
        return source_account_id;
    }

    public String getTarget_account_id() {
        return target_account_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getTx_date() {
        return tx_date;
    }

    public String getTime() {
        return time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getMonth() {
        return month;
    }
}
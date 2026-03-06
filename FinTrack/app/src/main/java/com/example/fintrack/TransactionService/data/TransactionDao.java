package com.example.fintrack.TransactionService.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fintrack.TransactionService.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionEntity transaction);

    @Query("SELECT * FROM transactions ORDER BY created_at DESC")
    List<TransactionEntity> getAll();
}
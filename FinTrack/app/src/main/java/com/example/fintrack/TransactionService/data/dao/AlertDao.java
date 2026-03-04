package com.example.fintrack.TransactionService.data.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fintrack.TransactionService.data.entity.AlertEntity;

@Dao
public interface AlertDao {

    @Query(
            "SELECT * FROM alerts " +
                    "WHERE user_id = :userId " +
                    "AND category_id = :categoryId " +
                    "LIMIT 1"
    )
    AlertEntity getByCategory(String userId, String categoryId);

    @Update
    void update(AlertEntity alert);
}

package com.example.fintrack.TransactionService.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    // ===== BASIC CRUD =====

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);

    @Delete
    void delete(TransactionEntity transaction);

    @Query("SELECT * FROM transactions WHERE tx_id = :txId LIMIT 1")
    TransactionEntity getById(String txId);

    // ===== HISTORY + SEARCH =====

    @Query(
            "SELECT * FROM transactions " +
                    "WHERE user_id = :userId " +
                    "AND (:txTypeId IS NULL OR tx_type_id = :txTypeId) " +
                    "AND ( " +
                    "   :accountId IS NULL OR " +
                    "   source_account_id = :accountId OR " +
                    "   target_account_id = :accountId " +
                    ") " +
                    "AND ( " +
                    "   :keyword IS NULL OR " +
                    "   note LIKE '%' || :keyword || '%' " +
                    ") " +
                    "AND ( " +
                    "   (:fromDate IS NULL OR tx_date >= :fromDate) " +
                    "   AND (:toDate IS NULL OR tx_date <= :toDate) " +
                    ") " +
                    "ORDER BY tx_date DESC, created_at DESC"
    )
    List<TransactionEntity> searchTransactions(
            String userId,
            String txTypeId,
            String accountId,
            String keyword,
            String fromDate,
            String toDate
    );
}

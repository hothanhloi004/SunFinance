package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.util.List;

public class SearchTransactionUseCase {

    private final TransactionDao transactionDao;

    public SearchTransactionUseCase(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    /**
     * @param userId     user đang đăng nhập (bắt buộc)
     * @param txTypeId   INCOME / EXPENSE / TRANSFER (nullable)
     * @param accountId  lọc theo ví (nullable)
     * @param keyword    tìm trong note (nullable)
     * @param fromDate   yyyy-MM-dd (nullable)
     * @param toDate     yyyy-MM-dd (nullable)
     */
    public List<TransactionEntity> execute(
            String userId,
            String txTypeId,
            String accountId,
            String keyword,
            String fromDate,
            String toDate
    ) {

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("UserId is required");
        }

        return transactionDao.searchTransactions(
                userId,
                txTypeId,
                accountId,
                keyword,
                fromDate,
                toDate
        );
    }
}

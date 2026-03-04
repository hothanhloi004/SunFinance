package com.example.fintrack.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    // Danh sách giao dịch (giả lập – sau này có thể thay bằng DB)
    private List<String> transactions;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    // Thêm giao dịch
    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    // Lấy danh sách giao dịch
    public List<String> getAllTransactions() {
        return transactions;
    }

    // Xóa giao dịch theo vị trí
    public void removeTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
        }
    }
}
// test123

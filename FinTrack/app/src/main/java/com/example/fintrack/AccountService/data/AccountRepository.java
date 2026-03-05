package com.example.fintrack.AccountService.data;

import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.AccountService.model.AccountTypeEntity;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    // 1. TẠO SINGLETON
    private static AccountRepository instance;

    // 2. GIỮ NGUYÊN MOCK DATA NHƯ CŨ
    private static List<AccountEntity> list = new ArrayList<>();
    private static List<String> transactionMockData = new ArrayList<>();
    private static List<AccountTypeEntity> accountTypes = new ArrayList<>();

    static {
        // Khởi tạo dữ liệu mẫu
        list.add(new AccountEntity("acc001", "u001", "Ví cá nhân", AccountEntity.TYPE_WALLET, 2000000));
        list.add(new AccountEntity("acc002", "u001", "Tài khoản Vietcombank", AccountEntity.TYPE_BANK, 15000000));
        transactionMockData.add("acc001");

        accountTypes.add(new AccountTypeEntity(AccountEntity.TYPE_WALLET, "Ví tiền", "Tiền mặt"));
        accountTypes.add(new AccountTypeEntity(AccountEntity.TYPE_BANK, "Ngân hàng", "Tài khoản ngân hàng"));
        accountTypes.add(new AccountTypeEntity("CREDIT", "Thẻ tín dụng", "Chi tiêu trước trả sau"));
        accountTypes.add(new AccountTypeEntity("SAVING", "Tiết kiệm", "Khoản tích lũy"));
    }

    private AccountRepository() {}

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }


    public List<AccountTypeEntity> getAccountTypes() { return accountTypes; }
    public List<AccountEntity> getMockData() { return list; }
    public boolean hasTransactions(String id) { return transactionMockData.contains(id); }
    public void addAccount(AccountEntity account) { list.add(account); }

    public void addAccountType(String id, String name, String description) {
        for (AccountTypeEntity t : accountTypes) {
            if (t.typeid.equals(id)) return;
        }
        accountTypes.add(new AccountTypeEntity(id, name, description));
    }

    public String generateNewId() {
        int maxId = 0;
        for (AccountEntity acc : list) {
            try {
                int idNum = Integer.parseInt(acc.accountId.replaceAll("[^0-9]", ""));
                if (idNum > maxId) maxId = idNum;
            } catch (Exception ignored) { }
        }
        return String.format("acc%03d", maxId + 1);
    }

    public void updateAccount(AccountEntity updatedAccount) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).accountId.equals(updatedAccount.accountId)) {
                list.set(i, updatedAccount);
                return;
            }
        }
    }

    public boolean isAccountTypeExists(String id) {
        for (AccountTypeEntity t : accountTypes) {
            if (t.typeid.equals(id)) return true;
        }
        return false;
    }

    public AccountEntity getAccountById(String id) {
        for (AccountEntity acc : list) {
            if (acc.accountId.equals(id)) return acc;
        }
        return null;
    }

    public boolean isAccountTypeInUse(String id) {
        for (AccountEntity a : list) {
            if (a.typeId.equals(id)) return true;
        }
        return false;
    }

    public void deleteAccount(String id) {
        list.removeIf(account -> account.accountId.equals(id));
    }

    public void deleteAccountType(String typeId) {
        if (typeId.equals(AccountEntity.TYPE_WALLET) || typeId.equals(AccountEntity.TYPE_BANK)) return;
        accountTypes.removeIf(type -> type.typeid.equals(typeId));
    }

    public void updateStatus(String id, String status) {
        for (AccountEntity acc : list) {
            if (acc.accountId.equals(id)) {
                acc.status = status;
                return;
            }
        }
    }
}
package com.example.fintrack.UserService.data;

import java.util.ArrayList;
import java.util.List;

import com.example.fintrack.UserService.entity.Account;
import com.example.fintrack.UserService.entity.AccountType;
import com.example.fintrack.UserService.entity.User;

public class FakeDataProvider {

    // ===== USER MOCK =====
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(
                "u001",
                "NguyenVanA@gmail.com",
                "Nguyen Van A",
                "PREMIUM MEMBER"
        ));

        return users;
    }

    // ===== ACCOUNT TYPES =====
    public static final AccountType WALLET =
            new AccountType("WALLET", "Ví tiền", "Tiền mặt");

    public static final AccountType BANK =
            new AccountType("BANK", "Ngân hàng", "Tài khoản ngân hàng");

    public static final AccountType CREDIT =
            new AccountType("CREDIT", "Thẻ tín dụng", "Chi tiêu trước trả sau");

    // ===== ACCOUNTS =====
    public static List<Account> getAccountsByUser(String userId) {
        List<Account> list = new ArrayList<>();

        list.add(new Account(
                "acc001", userId, "Ví cá nhân", WALLET, 2_000_000, "ACTIVE"
        ));
        list.add(new Account(
                "acc002", userId, "Tài khoản Vietcombank", BANK, 15_000_000, "ACTIVE"
        ));
        list.add(new Account(
                "acc003", userId, "Thẻ tín dụng Visa", CREDIT, -3_000_000, "ACTIVE"
        ));

        return list;
    }
}

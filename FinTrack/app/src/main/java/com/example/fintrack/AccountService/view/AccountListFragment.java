package com.example.fintrack.AccountService.view;
import com.example.fintrack.AccountService.api.AccountApiImpl;

public class AccountListFragment {
    private AccountApiImpl api = new AccountApiImpl();

    public void onUserClickDelete(String id) {
        api.deleteOrHideAccount(id); // Gọi API khi người dùng bấm xóa
    }
}
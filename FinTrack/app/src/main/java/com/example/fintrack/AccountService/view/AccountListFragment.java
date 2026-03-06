package com.example.fintrack.AccountService.view;

import android.content.Context;
import com.example.fintrack.AccountService.api.AccountApiImpl;

public class AccountListFragment {

    private AccountApiImpl api;

    public AccountListFragment(Context context) {
        api = new AccountApiImpl(context);
    }

    public void onUserClickDelete(String id) {
        api.deleteOrHideAccount(id);
    }

}
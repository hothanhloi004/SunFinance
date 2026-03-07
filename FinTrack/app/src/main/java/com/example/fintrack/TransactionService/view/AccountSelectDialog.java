package com.example.fintrack.TransactionService.view;

import android.app.AlertDialog;
import android.content.Context;

import java.util.List;
import java.text.DecimalFormat;
import com.example.fintrack.AccountService.model.AccountEntity;
public class AccountSelectDialog {

    public interface OnAccountSelected {
        void onSelect(AccountEntity account);
    }

    public static void show(
            Context context,
            List<AccountEntity> accounts,
            OnAccountSelected listener
    ) {

        String[] items = new String[accounts.size()];

        for (int i = 0; i < accounts.size(); i++) {
            AccountEntity acc = accounts.get(i);

            DecimalFormat df = new DecimalFormat("#,###");

            items[i] = acc.name
                    + "\nBalance: "
                    + df.format(acc.balance)
                    + " VND";
        }

        new AlertDialog.Builder(context)
                .setTitle("Select Wallet")
                .setItems(items, (dialog, which) ->
                        listener.onSelect(accounts.get(which)))
                .show();
    }
}
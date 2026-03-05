package com.example.fintrack.AccountService.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fintrack.AccountService.data.AccountRepository;
import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.UserService.view.UserAccountProfileActivity;
import com.example.fintrack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountDashboardActivity extends AppCompatActivity {
    private TextView tvTotalBalance, tvAccountCount;
    private RecyclerView rvWallets;
    private AccountRepository accountRepo = AccountRepository.getInstance();

    private final List<AccountEntity> accountList = new ArrayList<>();
    private AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wallet);

        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        tvAccountCount = findViewById(R.id.tvAccountCount);
        rvWallets = findViewById(R.id.rvWallets);
        FloatingActionButton btnAddWallet = findViewById(R.id.btnAddWallet);
        ImageButton btnMenuMore = findViewById(R.id.btnMenuMore);
        LinearLayout btnNavSetting = findViewById(R.id.btnNavSettings);

        adapter = new AccountAdapter(accountList);
        rvWallets.setLayoutManager(new LinearLayoutManager(this));
        rvWallets.setNestedScrollingEnabled(false);
        rvWallets.setAdapter(adapter);

        btnNavSetting.setOnClickListener(v -> startActivity(new Intent(this, UserAccountProfileActivity.class)));
        btnAddWallet.setOnClickListener(v -> startActivity(new Intent(this, ManageWalletActivity.class)));

        btnMenuMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(AccountDashboardActivity.this, v);
            popup.getMenu().add(0, 1, 0, "Active Wallets");
            popup.getMenu().add(0, 2, 1, "Hidden Wallets");
            popup.getMenu().add(0, 3, 2, "Manage Wallet Types");

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case 1: startActivity(new Intent(this, ActiveWalletsActivity.class)); return true;
                    case 2: startActivity(new Intent(this, HiddenWalletsActivity.class)); return true;
                    case 3: startActivity(new Intent(this, WalletTypeActivity.class)); return true;
                }
                return false;
            });
            popup.show();
        });
    }

    private void updateUI() {
        accountList.clear();
        double total = 0;
        for (AccountEntity acc : accountRepo.getMockData()) {
            // Đã dùng Hằng số STATUS_ACTIVE thay vì chuỗi
            if (acc.status == null || acc.status.isEmpty() || acc.status.equalsIgnoreCase(AccountEntity.STATUS_ACTIVE)) {
                accountList.add(acc);
                total += acc.balance;
            }
        }
        DecimalFormat df = new DecimalFormat("#,###");
        tvTotalBalance.setText(df.format(total) + " đ");
        tvAccountCount.setText("Checking across " + accountList.size() + " accounts");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
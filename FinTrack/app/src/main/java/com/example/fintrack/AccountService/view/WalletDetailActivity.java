package com.example.fintrack.AccountService.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fintrack.AccountService.data.AccountRepository;
import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.AccountService.usecase.DeleteAccountUseCase;
import com.example.fintrack.R;
import com.google.android.material.button.MaterialButton;
import java.text.DecimalFormat;

public class WalletDetailActivity extends AppCompatActivity {
    private TextView txtName, txtId, txtBalance;
    private AccountRepository repo = AccountRepository.getInstance(); // Dùng Singleton
    private String walletId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wallet);

        txtName = findViewById(R.id.txtDetailWalletName);
        txtId = findViewById(R.id.txtDetailWalletId);
        txtBalance = findViewById(R.id.txtDetailBalance);
        ImageButton btnBack = findViewById(R.id.btnBackDetail);
        ImageButton btnMenuMore = findViewById(R.id.btnMenuMore);
        MaterialButton btnEdit = findViewById(R.id.btnEditWallet);
        MaterialButton btnDelete = findViewById(R.id.btnDeleteWallet);

        walletId = getIntent().getStringExtra("WALLET_ID");
        displayWalletInfo(walletId);

        btnBack.setOnClickListener(v -> finish());
        btnMenuMore.setOnClickListener(v -> showArchiveDialog());

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageWalletActivity.class);
            intent.putExtra("EDIT_MODE", true);
            intent.putExtra("WALLET_ID", walletId);
            startActivity(intent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            boolean hasTransactions = repo.hasTransactions(walletId);
            new AlertDialog.Builder(this)
                    .setTitle(!hasTransactions ? "Delete Confirmation" : "Archive Confirmation")
                    .setMessage(!hasTransactions
                            ? "This wallet has no transactions. Do you want to permanently DELETE it?"
                            : "This wallet already has transactions. The system will ARCHIVE it from the main list.")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        new DeleteAccountUseCase().execute(walletId);
                        Toast.makeText(this, "Operation completed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void displayWalletInfo(String id) {
        AccountEntity account = repo.getAccountById(id); // Dùng hàm thay vì vòng lặp
        if (account != null) {
            txtName.setText(account.name);
            txtId.setText("WALLET ID: " + account.accountId);
            DecimalFormat df = new DecimalFormat("#,###");
            txtBalance.setText(df.format(account.balance) + " VND");
        }
    }

    private void showArchiveDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Archive Wallet Confirmation")
                .setMessage("Do you want to archive this wallet?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    repo.updateStatus(walletId, AccountEntity.STATUS_HIDDEN);
                    Toast.makeText(this, "Wallet archived successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
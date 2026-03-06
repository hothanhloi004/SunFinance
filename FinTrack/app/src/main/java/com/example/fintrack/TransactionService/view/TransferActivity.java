package com.example.fintrack.TransactionService.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.AccountEntity;
import com.example.fintrack.TransactionService.domain.usecase.TransferMoneyUseCase;

import java.text.DecimalFormat;
import java.util.List;
import com.example.fintrack.AccountService.api.AccountApiImpl;
public class TransferActivity extends AppCompatActivity {

    private final DecimalFormat df = new DecimalFormat("#,###");

    private TextView tvSourceAccount, tvSourceBalance;
    private TextView tvTargetAccount, tvTargetBalance;

    private Button btnChangeSource, btnChangeTarget;
    private Button btnTransfer, btn500k, btn1m, btn5m;

    private EditText edtAmount, edtNote;

    private String selectedSourceId;
    private String selectedTargetId;

    private List<AccountEntity> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initViews();
        loadAccounts();
        setupPresetButtons();
        setupTransfer();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadAccounts();     // reload lại list accounts
    }

    private void initViews() {
        tvSourceAccount = findViewById(R.id.tvSourceAccount);
        tvSourceBalance = findViewById(R.id.tvSourceBalance);

        tvTargetAccount = findViewById(R.id.tvTargetAccount);
        tvTargetBalance = findViewById(R.id.tvTargetBalance);

        btnChangeSource = findViewById(R.id.btnChangeSource);
        btnChangeTarget = findViewById(R.id.btnChangeTarget);

        btnTransfer = findViewById(R.id.btnTransfer);
        btn500k = findViewById(R.id.btn500k);
        btn1m = findViewById(R.id.btn1m);
        btn5m = findViewById(R.id.btn5m);

        edtAmount = findViewById(R.id.edtAmount);
        edtNote = findViewById(R.id.edtNote);
    }

    private void loadAccounts() {

        new Thread(() -> {

            FintrackDatabase db =
                    FintrackDatabase.getInstance(getApplicationContext());

            List<AccountEntity> result =
                    db.accountDao().getAccountsByUser("u001");

            runOnUiThread(() -> {

                accounts = result;

                if (accounts == null || accounts.size() < 2) {
                    Toast.makeText(this,
                            "Database chưa có 2 ví",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // giữ lại ví đã chọn nếu còn tồn tại
                AccountEntity source = null;
                AccountEntity target = null;

                for (AccountEntity acc : accounts) {
                    if (acc.account_id.equals(selectedSourceId)) {
                        source = acc;
                    }
                    if (acc.account_id.equals(selectedTargetId)) {
                        target = acc;
                    }
                }

                // nếu chưa chọn ví → dùng mặc định
                if (source == null) source = accounts.get(0);
                if (target == null) target = accounts.get(1);

                selectedSourceId = source.account_id;
                selectedTargetId = target.account_id;

                tvSourceAccount.setText(source.name);
                tvSourceBalance.setText("Balance: " + df.format(source.balance) + " VND");

                tvTargetAccount.setText(target.name);
                tvTargetBalance.setText("Balance: " + df.format(target.balance) + " VND");

                setupWalletSelectors();
            });

        }).start();
    }

    private void setupWalletSelectors() {

        btnChangeSource.setOnClickListener(v ->
                AccountSelectDialog.show(
                        this,
                        accounts,
                        account -> {
                            selectedSourceId = account.account_id;
                            tvSourceAccount.setText(account.name);
                            tvSourceBalance.setText(
                                    "Balance: " + df.format(account.balance) + " VND"
                            );
                        }
                )
        );

        btnChangeTarget.setOnClickListener(v ->
                AccountSelectDialog.show(
                        this,
                        accounts,
                        account -> {
                            selectedTargetId = account.account_id;
                            tvTargetAccount.setText(account.name);
                            tvTargetBalance.setText(
                                    "Balance: " + df.format(account.balance) + " VND"
                            );
                        }
                )
        );
    }

    private void setupPresetButtons() {
        btn500k.setOnClickListener(v -> addAmount(500_000));
        btn1m.setOnClickListener(v -> addAmount(1_000_000));
        btn5m.setOnClickListener(v -> addAmount(5_000_000));
    }

    private void setupTransfer() {

        btnTransfer.setOnClickListener(v -> {

            if (selectedSourceId == null || selectedTargetId == null) {
                Toast.makeText(this,
                        "Vui lòng chọn ví",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedSourceId.equals(selectedTargetId)) {
                Toast.makeText(this,
                        "Source và Target không được trùng",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String amountStr = edtAmount.getText()
                    .toString()
                    .replaceAll("[^0-9]", "");

            if (amountStr.isEmpty()) {
                Toast.makeText(this,
                        "Vui lòng nhập số tiền",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String note = edtNote.getText().toString().trim();

            executeTransfer(amount, note);
        });
    }

    private void executeTransfer(double amount, String note) {

        new Thread(() -> {
            try {

                FintrackDatabase db =
                        FintrackDatabase.getInstance(getApplicationContext());

                AccountApiImpl accountApi =
                        new AccountApiImpl(getApplicationContext());

                TransferMoneyUseCase useCase =
                        new TransferMoneyUseCase(
                                db.transactionDao(),
                                accountApi
                        );

                useCase.execute(
                        "u001",
                        selectedSourceId,
                        selectedTargetId,
                        amount,
                        note
                );

                runOnUiThread(() -> {
                    Toast.makeText(this,
                            "Chuyển tiền thành công",
                            Toast.LENGTH_SHORT).show();
                    loadAccounts();      // cập nhật lại list accounts
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private void addAmount(int add) {
        String cur = edtAmount.getText()
                .toString()
                .replaceAll("[^0-9]", "");

        long val = cur.isEmpty() ? 0 : Long.parseLong(cur);
        val += add;

        edtAmount.setText(String.valueOf(val));
    }
    private void refreshSelectedBalances() {
        if (accounts == null) return;

        for (AccountEntity acc : accounts) {
            if (acc.account_id.equals(selectedSourceId)) {
                tvSourceBalance.setText("Balance: " + df.format(acc.balance) + " VND");
            }
            if (acc.account_id.equals(selectedTargetId)) {
                tvTargetBalance.setText("Balance: " + df.format(acc.balance) + " VND");
            }
        }
    }
    private void reloadBalance() {

        new Thread(() -> {

            FintrackDatabase db =
                    FintrackDatabase.getInstance(getApplicationContext());

            AccountEntity source =
                    db.accountDao().getById(selectedSourceId);

            AccountEntity target =
                    db.accountDao().getById(selectedTargetId);

            runOnUiThread(() -> {

                if (source != null) {
                    tvSourceBalance.setText(
                            "Balance: " + df.format(source.balance) + " VND"
                    );
                }

                if (target != null) {
                    tvTargetBalance.setText(
                            "Balance: " + df.format(target.balance) + " VND"
                    );
                }

            });

        }).start();
    }
}
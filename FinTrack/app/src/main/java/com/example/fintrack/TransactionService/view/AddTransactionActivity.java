package com.example.fintrack.TransactionService.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ExperimentalGetImage;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.domain.usecase.AddTransactionUseCase;
import com.example.fintrack.AccountService.api.AccountApiImpl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.example.fintrack.AccountService.model.AccountEntity;

public class AddTransactionActivity extends AppCompatActivity {

    private final DecimalFormat df = new DecimalFormat("#,###");

    private EditText edtAmount, edtNote;
    private Button btnIncome, btnExpense, btnSave;
    private TextView txtCategoryName, txtCategoryIcon;
    private TextView txtAccountName, txtBalance;
    private TextView txtDateTime;

    private String currentTxType = "EXPENSE";
    private String selectedCategoryId = null;
    private String selectedAccountId = null;

    private LocalDate selectedDate;
    private LocalTime selectedTime;

    private List<AccountEntity> accounts;

    private AccountApiImpl accountApi;

    @OptIn(markerClass = ExperimentalGetImage.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_v2);

        accountApi = new AccountApiImpl(getApplicationContext());

        initViews();

        selectedDate = LocalDate.now();
        selectedTime = LocalTime.now();
        updateDateTimeText();

        Button btnScanReceipt;
        receiveScanData();

        loadAccounts();

        findViewById(R.id.layoutDateTime)
                .setOnClickListener(v -> openDatePicker());

        setupToggleButtons();
        setupCategoryPicker();

        // ===== THÊM CODE NHẬN DỮ LIỆU CSV =====
        readCSVData();

        btnSave.setOnClickListener(v -> addTransaction());

        Button btnImportCSV = findViewById(R.id.btnImportCSV);

        btnImportCSV.setOnClickListener(v -> {
            Intent intent = new Intent(AddTransactionActivity.this, ImportBankStatementActivity.class);
            startActivity(intent);
        });

        btnScanReceipt = findViewById(R.id.btnScanReceipt);
        btnScanReceipt.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AddTransactionActivity.this,
                    ScanReceiptActivity.class
            );

            startActivity(intent);
        });
    }

    private void initViews() {

        edtAmount = findViewById(R.id.edtAmount);
        edtNote = findViewById(R.id.edtNote);

        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        btnSave = findViewById(R.id.btnSave);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtCategoryIcon = findViewById(R.id.txtCategoryIcon);

        txtAccountName = findViewById(R.id.txtAccountName);
        txtBalance = findViewById(R.id.txtBalance);

        txtDateTime = findViewById(R.id.txtDateTime);
    }

    private void loadAccounts() {

        new Thread(() -> {

            accounts = accountApi.getAccountsByUser("u001");

            runOnUiThread(() -> {

                if (accounts == null || accounts.isEmpty()) {

                    Toast.makeText(this,
                            "Chưa có ví",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                AccountEntity acc = accounts.get(0);

                selectedAccountId = acc.accountId;

                txtAccountName.setText(acc.name);
                txtBalance.setText("Số dư: " + df.format(acc.balance) + " đ");

                findViewById(R.id.layoutAccount)
                        .setOnClickListener(v ->
                                AccountSelectDialog.show(
                                        this,
                                        accounts,
                                        account -> {

                                            selectedAccountId = account.accountId;

                                            txtAccountName.setText(account.name);

                                            txtBalance.setText(
                                                    "Số dư: " + df.format(account.balance) + " đ"
                                            );
                                        }
                                )
                        );
            });

        }).start();
    }

    private void setupToggleButtons() {

        btnExpense.setOnClickListener(v -> {
            currentTxType = "EXPENSE";
            switchToggle(true);
        });

        btnIncome.setOnClickListener(v -> {
            currentTxType = "INCOME";
            switchToggle(false);
        });
    }

    private void switchToggle(boolean expense) {

        if (expense) {

            btnExpense.setBackgroundResource(R.drawable.bg_toggle_active);
            btnExpense.setTextColor(Color.WHITE);

            btnIncome.setBackgroundResource(R.drawable.bg_toggle_inactive);
            btnIncome.setTextColor(Color.GRAY);

        } else {

            btnIncome.setBackgroundResource(R.drawable.bg_toggle_active);
            btnIncome.setTextColor(Color.WHITE);

            btnExpense.setBackgroundResource(R.drawable.bg_toggle_inactive);
            btnExpense.setTextColor(Color.GRAY);
        }

        selectedCategoryId = null;
        txtCategoryName.setText("Chọn danh mục");
        txtCategoryIcon.setText("📂");
    }

    private void setupCategoryPicker() {

        findViewById(R.id.layoutCategory)
                .setOnClickListener(v -> {

                    CategoryPickerBottomSheet sheet =
                            CategoryPickerBottomSheet.newInstance(
                                    currentTxType,
                                    category -> {

                                        txtCategoryName.setText(category.name);
                                        txtCategoryIcon.setText(category.icon);

                                        selectedCategoryId = category.category_id;
                                    }
                            );

                    sheet.show(getSupportFragmentManager(), "category_picker");
                });
    }

    private void updateDateTimeText() {

        txtDateTime.setText(
                selectedDate + ", " +
                        String.format("%02d:%02d",
                                selectedTime.getHour(),
                                selectedTime.getMinute())
        );
    }

    private void openDatePicker() {

        LocalDate now = LocalDate.now();

        new DatePickerDialog(
                this,
                (view, y, m, d) -> {

                    selectedDate = LocalDate.of(y, m + 1, d);
                    openTimePicker();

                },
                now.getYear(),
                now.getMonthValue() - 1,
                now.getDayOfMonth()
        ).show();
    }

    private void openTimePicker() {

        LocalTime now = LocalTime.now();

        new TimePickerDialog(
                this,
                (view, h, min) -> {

                    selectedTime = LocalTime.of(h, min);
                    updateDateTimeText();

                },
                now.getHour(),
                now.getMinute(),
                true
        ).show();
    }

    // ===== HÀM MỚI NHẬN DỮ LIỆU CSV =====

    private void readCSVData() {

        Intent intent = getIntent();

        if (intent == null) return;

        String date = intent.getStringExtra("csv_date");
        double amount = intent.getDoubleExtra("csv_amount", 0);
        String note = intent.getStringExtra("csv_note");
        String type = intent.getStringExtra("csv_type");

        if (amount != 0) {
            edtAmount.setText(String.valueOf(amount));
        }

        if (note != null) {
            edtNote.setText(note);
        }

        if (date != null) {

            try {

                selectedDate = LocalDate.parse(date);
                updateDateTimeText();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (type != null) {

            currentTxType = type;

            if ("EXPENSE".equals(type)) {
                switchToggle(true);
            } else {
                switchToggle(false);
            }
        }
    }

    private void addTransaction() {

        String amountStr = edtAmount.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        if (amountStr.isEmpty()) {

            Toast.makeText(this,
                    "Vui lòng nhập số tiền",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategoryId == null) {

            Toast.makeText(this,
                    "Vui lòng chọn danh mục",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String date = selectedDate.toString();

        new Thread(() -> {

            try {

                FintrackDatabase db =
                        FintrackDatabase.getInstance(getApplicationContext());

                new AddTransactionUseCase(
                        getApplicationContext(),
                        db.transactionDao(),
                        db.alertDao(),
                        accountApi
                ).execute(
                        "u001",
                        currentTxType,
                        selectedAccountId,
                        selectedCategoryId,
                        amount,
                        note,
                        date
                );

                runOnUiThread(() -> {

                    Toast.makeText(this,
                            "Đã thêm giao dịch",
                            Toast.LENGTH_SHORT).show();

                    loadAccounts();
                });

            } catch (Exception e) {

                runOnUiThread(() ->
                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
            }

        }).start();
    }

    private void receiveScanData() {

        if (getIntent() == null) return;

        String amount = getIntent().getStringExtra("amount");
        String date = getIntent().getStringExtra("date");
        String merchant = getIntent().getStringExtra("merchant");

        if (amount != null) {
            edtAmount.setText(amount);
        }

        if (merchant != null) {
            edtNote.setText(merchant);
        }

        if (date != null && !date.isEmpty()) {
            try {
                selectedDate = LocalDate.parse(date);
                updateDateTimeText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
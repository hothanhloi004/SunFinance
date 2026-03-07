package com.example.fintrack.AlertService.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.TransactionRepository;
import com.example.fintrack.TransactionService.entity.Transaction;
import com.example.fintrack.core.database.DatabaseHelper;

import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    Spinner spCategory, spPeriod;
    EditText etAmount;
    CheckBox cbNotify;
    Button btnCreate;

    TextView tvDiningAmount, tvDiningWarning;
    ProgressBar pbDining;

    TextView tvShoppingAmount, tvShoppingWarning;
    ProgressBar pbShopping;

    TextView tvTransportAmount, tvTransportWarning;
    ProgressBar pbTransport;

    // 🔴 THÊM BIẾN TOTAL
    TextView tvTotalSpent;

    // Dữ liệu chi tiêu thật
    double spentDining = 0;
    double spentShopping = 0;
    double spentTransport = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        spCategory = findViewById(R.id.spCategory);
        spPeriod = findViewById(R.id.spPeriod);
        etAmount = findViewById(R.id.etAmount);
        cbNotify = findViewById(R.id.cbNotify);
        btnCreate = findViewById(R.id.btnCreate);

        tvDiningAmount = findViewById(R.id.tvDiningAmount);
        tvDiningWarning = findViewById(R.id.tvDiningWarning);
        pbDining = findViewById(R.id.pbDining);

        tvShoppingAmount = findViewById(R.id.tvShoppingAmount);
        tvShoppingWarning = findViewById(R.id.tvShoppingWarning);
        pbShopping = findViewById(R.id.pbShopping);

        tvTransportAmount = findViewById(R.id.tvTransportAmount);
        tvTransportWarning = findViewById(R.id.tvTransportWarning);
        pbTransport = findViewById(R.id.pbTransport);

        // 🔴 FIND TOTAL TEXTVIEW
        tvTotalSpent = findViewById(R.id.tvTotalSpent);

        // Spinner Category
        String[] categories = {"Dining", "Shopping", "Transport"};

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categories
                );

        spCategory.setAdapter(categoryAdapter);

        // Spinner Period
        String[] periods = {"Daily", "Weekly", "Monthly"};

        ArrayAdapter<String> periodAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        periods
                );

        spPeriod.setAdapter(periodAdapter);

        // LOAD DATA
        loadSpentData();

        btnCreate.setOnClickListener(v -> {

            String amountStr = etAmount.getText().toString().trim();

            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double limit = Double.parseDouble(amountStr);
            boolean notify = cbNotify.isChecked();
            String category = spCategory.getSelectedItem().toString();

            switch (category) {

                case "Dining":
                    updateUI(spentDining, limit, notify,
                            tvDiningAmount, tvDiningWarning, pbDining);
                    break;

                case "Shopping":
                    updateUI(spentShopping, limit, notify,
                            tvShoppingAmount, tvShoppingWarning, pbShopping);
                    break;

                case "Transport":
                    updateUI(spentTransport, limit, notify,
                            tvTransportAmount, tvTransportWarning, pbTransport);
                    break;
            }

            Toast.makeText(this, "Budget Created!", Toast.LENGTH_SHORT).show();
        });
    }

    // 🔴 TÍNH CHI TIÊU TỪ DATABASE
    private void loadSpentData() {

        TransactionRepository repo =
                new TransactionRepository(
                        DatabaseHelper.getDB(this)
                );

        List<Transaction> list = repo.findAll();

        spentDining = 0;
        spentShopping = 0;
        spentTransport = 0;

        double totalSpent = 0;

        for (Transaction t : list) {

            if (!t.type.equals("EXPENSE"))
                continue;

            totalSpent += t.amount;

            switch (t.category) {

                case "Dining":
                    spentDining += t.amount;
                    break;

                case "Shopping":
                    spentShopping += t.amount;
                    break;

                case "Transport":
                    spentTransport += t.amount;
                    break;
            }
        }

        // 🔴 HIỂN THỊ TỔNG CHI TIÊU
        tvTotalSpent.setText(String.format("%,.0f VND", totalSpent));

        initDefaultData();
    }

    private void initDefaultData() {

        tvDiningAmount.setText(String.format("%.0f VND spent", spentDining));
        tvShoppingAmount.setText(String.format("%.0f VND spent", spentShopping));
        tvTransportAmount.setText(String.format("%.0f VND spent", spentTransport));

        pbDining.setProgress(0);
        pbShopping.setProgress(0);
        pbTransport.setProgress(0);

        pbDining.getProgressDrawable().setColorFilter(
                getResources().getColor(android.R.color.holo_green_dark),
                PorterDuff.Mode.SRC_IN);

        pbShopping.getProgressDrawable().setColorFilter(
                getResources().getColor(android.R.color.holo_green_dark),
                PorterDuff.Mode.SRC_IN);

        pbTransport.getProgressDrawable().setColorFilter(
                getResources().getColor(android.R.color.holo_green_dark),
                PorterDuff.Mode.SRC_IN);
    }

    private void updateUI(double spent,
                          double limit,
                          boolean notify,
                          TextView tvAmount,
                          TextView tvWarning,
                          ProgressBar progressBar) {

        if (limit <= 0) {
            tvAmount.setText("No budget set");
            tvWarning.setVisibility(View.GONE);
            progressBar.setProgress(0);
            return;
        }

        tvAmount.setText(String.format("%.0f / %.0f VND", spent, limit));

        int percent = (int) ((spent / limit) * 100);

        int displayPercent = percent;
        if (displayPercent > 100) displayPercent = 100;

        progressBar.setProgress(displayPercent);

        if (percent < 80) {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_green_dark),
                    PorterDuff.Mode.SRC_IN);

        } else if (percent < 100) {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_orange_dark),
                    PorterDuff.Mode.SRC_IN);

        } else {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_red_dark),
                    PorterDuff.Mode.SRC_IN);
        }

        if (notify && percent >= 80 && percent < 100) {

            tvWarning.setText("⚠ Warning: Exceeded 80% limit");
            tvWarning.setVisibility(View.VISIBLE);

        } else if (notify && percent >= 100) {

            tvWarning.setText("⚠ Exceeded budget!");
            tvWarning.setVisibility(View.VISIBLE);

        } else {

            tvWarning.setVisibility(View.GONE);
        }
    }
}
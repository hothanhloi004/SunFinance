package com.example.fintrack.AlertService.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    private Spinner spCategory, spPeriod;
    private EditText etAmount;
    private CheckBox cbNotify;
    private Button btnCreate;

    private TextView tvDiningAmount, tvDiningWarning;
    private ProgressBar pbDining;

    private TextView tvShoppingAmount, tvShoppingWarning;
    private ProgressBar pbShopping;

    private TextView tvTransportAmount, tvTransportWarning;
    private ProgressBar pbTransport;

    private TextView tvTotalSpent;

    private double spentDining = 0;
    private double spentShopping = 0;
    private double spentTransport = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        initViews();
        initSpinners();
        loadSpentData();
        setupCreateButton();
    }

    private void initViews() {

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

        tvTotalSpent = findViewById(R.id.tvTotalSpent);

        pbDining.setMax(100);
        pbShopping.setMax(100);
        pbTransport.setMax(100);
    }

    private void initSpinners() {

        String[] categories = {"Dining", "Shopping", "Transport"};

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categories);

        spCategory.setAdapter(categoryAdapter);

        String[] periods = {"Daily", "Weekly", "Monthly"};

        ArrayAdapter<String> periodAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        periods);

        spPeriod.setAdapter(periodAdapter);
    }

    private void loadSpentData() {

        new Thread(() -> {

            FintrackDatabase db =
                    FintrackDatabase.getInstance(getApplicationContext());

            List<TransactionEntity> list =
                    db.transactionDao().getAll();

            double totalSpent = 0;

            for (TransactionEntity t : list) {

                if (!"EXPENSE".equals(t.tx_type_id))
                    continue;

                totalSpent += t.amount;

                if ("FOOD".equals(t.category_id))
                    spentDining += t.amount;

                else if ("SHOPPING".equals(t.category_id))
                    spentShopping += t.amount;

                else if ("MOVE".equals(t.category_id))
                    spentTransport += t.amount;
            }

            double finalTotal = totalSpent;

            runOnUiThread(() -> {

                tvTotalSpent.setText(
                        String.format("%,.0f VND", finalTotal)
                );

                initDefaultData();
            });

        }).start();
    }

    private void initDefaultData() {

        tvDiningAmount.setText(String.format("%,.0f VND spent", spentDining));
        tvShoppingAmount.setText(String.format("%,.0f VND spent", spentShopping));
        tvTransportAmount.setText(String.format("%,.0f VND spent", spentTransport));

        pbDining.setProgress(0);
        pbShopping.setProgress(0);
        pbTransport.setProgress(0);
    }

    private void setupCreateButton() {

        btnCreate.setOnClickListener(v -> {

            String amountStr = etAmount.getText().toString().trim();

            if (amountStr.isEmpty()) {
                Toast.makeText(this,"Enter amount",Toast.LENGTH_SHORT).show();
                return;
            }

            double limit = Double.parseDouble(amountStr);
            boolean notify = cbNotify.isChecked();

            String category = spCategory.getSelectedItem().toString();

            switch (category) {

                case "Dining":
                    updateUI(spentDining, limit, notify,
                            tvDiningAmount,
                            tvDiningWarning,
                            pbDining);
                    break;

                case "Shopping":
                    updateUI(spentShopping, limit, notify,
                            tvShoppingAmount,
                            tvShoppingWarning,
                            pbShopping);
                    break;

                case "Transport":
                    updateUI(spentTransport, limit, notify,
                            tvTransportAmount,
                            tvTransportWarning,
                            pbTransport);
                    break;
            }

            Toast.makeText(this,"Budget created!",Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUI(double spent,
                          double limit,
                          boolean notify,
                          TextView tvAmount,
                          TextView tvWarning,
                          ProgressBar progressBar) {

        if (limit <= 0) return;

        int percent = (int) ((spent / limit) * 100);
        int displayPercent = Math.min(percent, 100);

        tvAmount.setText(String.format("%,.0f / %,.0f VND", spent, limit));
        progressBar.setProgress(displayPercent);

        if (percent < 80) {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_green_dark),
                    PorterDuff.Mode.SRC_IN);

        } else if (percent < 100) {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_orange_dark),
                    PorterDuff.Mode.SRC_IN);

            if (notify) {
                tvWarning.setText("⚠ 80% budget reached");
                tvWarning.setVisibility(View.VISIBLE);
            }

        } else {

            progressBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(android.R.color.holo_red_dark),
                    PorterDuff.Mode.SRC_IN);

            if (notify) {
                tvWarning.setText("🚨 Budget exceeded!");
                tvWarning.setVisibility(View.VISIBLE);
            }
        }
    }
}
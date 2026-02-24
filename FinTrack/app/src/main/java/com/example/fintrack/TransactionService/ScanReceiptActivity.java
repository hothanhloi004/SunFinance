package com.example.fintrack.TransactionService;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;

public class ScanReceiptActivity extends AppCompatActivity {

    TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receipt);

        txtTotalAmount = findViewById(R.id.txtTotalAmount);

        // Giả lập dữ liệu OCR trả về
        updateTotalAmount(55000);
    }

    private void updateTotalAmount(double total) {
        txtTotalAmount.setText(String.format("%,.0f VND", total));
    }
}
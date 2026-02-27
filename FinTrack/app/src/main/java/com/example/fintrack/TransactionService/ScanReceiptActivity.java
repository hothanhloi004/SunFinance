package com.example.fintrack.TransactionService;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fintrack.R;

public class ScanReceiptActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receipt);

        txtTotalAmount = findViewById(R.id.txtTotalAmount);

        // ✅ Xin quyền camera đúng chỗ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        }

        // Giả lập dữ liệu OCR trả về
        updateTotalAmount(55000);
    }

    private void updateTotalAmount(double total) {
        txtTotalAmount.setText(String.format("%,.0f VND", total));
    }
}
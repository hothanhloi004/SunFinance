package com.example.fintrack.TransactionService.view;

import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.domain.usecase.DeleteTransactionUseCase;
import com.example.fintrack.TransactionService.domain.usecase.UpdateTransactionUseCase;

public class UpdateDeleteTransactionActivity extends AppCompatActivity {

    private String txId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_transaction);

        txId = getIntent().getStringExtra("TX_ID");

        EditText edtAmount = findViewById(R.id.edtAmount);
        EditText edtNote = findViewById(R.id.edtNote);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(v -> {

            double newAmount =
                    Double.parseDouble(edtAmount.getText().toString());
            String newNote = edtNote.getText().toString();

            new Thread(() -> {
                FintrackDatabase db =
                        FintrackDatabase.getInstance(getApplicationContext());

                UpdateTransactionUseCase useCase =
                        new UpdateTransactionUseCase(
                                db.transactionDao(),
                                db.accountDao()
                        );

                // 🔑 LẤY TRANSACTION CŨ
                TransactionEntity oldTx =
                        db.transactionDao().getById(txId);

                // 🔑 GIỮ NGUYÊN CÁC FIELD CHƯA CHO SỬA
                useCase.execute(
                        txId,
                        newAmount,
                        newNote,
                        oldTx.tx_type_id.equals("EXPENSE")
                                ? oldTx.source_account_id
                                : oldTx.target_account_id,
                        oldTx.category_id,
                        oldTx.tx_type_id,
                        oldTx.tx_date
                );

                runOnUiThread(() -> {
                    Toast.makeText(
                            this,
                            "Cập nhật thành công",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                });
            }).start();
        });
        btnDelete.setOnClickListener(v -> {

            new Thread(() -> {
                FintrackDatabase db =
                        FintrackDatabase.getInstance(getApplicationContext());

                DeleteTransactionUseCase deleteUseCase =
                        new DeleteTransactionUseCase(
                                db.transactionDao(),
                                db.accountDao()
                        );

                deleteUseCase.execute(txId);

                runOnUiThread(() -> {
                    Toast.makeText(
                            this,
                            "Đã xoá giao dịch",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                });
            }).start();
        });

    }
}

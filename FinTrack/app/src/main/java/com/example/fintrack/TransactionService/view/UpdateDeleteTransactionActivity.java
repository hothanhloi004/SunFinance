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
import com.example.fintrack.AccountService.api.AccountApiImpl;

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

            String amountText = edtAmount.getText().toString();

            if (amountText.isEmpty()) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Amount required", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            double newAmount = Double.parseDouble(amountText);
            String newNote = edtNote.getText().toString();

            new Thread(() -> {
                FintrackDatabase db =
                        FintrackDatabase.getInstance(getApplicationContext());

                UpdateTransactionUseCase useCase =
                        new UpdateTransactionUseCase(
                                db.transactionDao(),
                                new AccountApiImpl(getApplicationContext())
                        );

                // 🔑 LẤY TRANSACTION CŨ
                TransactionEntity oldTx =
                        db.transactionDao().getById(txId);

                if (oldTx == null) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                String accountId;

                if ("EXPENSE".equals(oldTx.tx_type_id)) {
                    accountId = oldTx.source_account_id;
                } else if ("INCOME".equals(oldTx.tx_type_id)) {
                    accountId = oldTx.target_account_id;
                } else {
                    accountId = null;
                }

                useCase.execute(
                        txId,
                        newAmount,
                        newNote,
                        accountId,
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

                    setResult(RESULT_OK);
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
                                new AccountApiImpl(getApplicationContext())
                        );

                deleteUseCase.execute(txId);

                runOnUiThread(() -> {
                    Toast.makeText(
                            this,
                            "Đã xoá giao dịch",
                            Toast.LENGTH_SHORT
                    ).show();

                    setResult(RESULT_OK);
                    finish();
                });
            }).start();
        });

    }
}

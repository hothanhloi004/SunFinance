package com.example.fintrack.TransactionService.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.AppDatabase;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportBankStatementActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1001;

    private Button btnSelectFile;
    private Button btnStartImport;
    private TextView txtFileName;

    private Uri selectedFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_bank_statement);

        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnStartImport = findViewById(R.id.btnStartImport);
        txtFileName = findViewById(R.id.txtFileName);

        btnSelectFile.setOnClickListener(v -> openFilePicker());

        btnStartImport.setOnClickListener(v -> {
            if (selectedFileUri == null) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
                return;
            }

            importCSV();
        });
    }

    // ===============================
    // OPEN FILE PICKER
    // ===============================

    private void openFilePicker() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Select CSV File"), PICK_FILE_REQUEST);
    }

    // ===============================
    // RESULT FILE PICKER
    // ===============================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {

            selectedFileUri = data.getData();

            String fileName = getFileName(selectedFileUri);

            txtFileName.setText(fileName);
        }
    }

    // ===============================
    // GET FILE NAME
    // ===============================

    private String getFileName(Uri uri) {

        String result = null;

        if (uri.getScheme().equals("content")) {

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            try {

                if (cursor != null && cursor.moveToFirst()) {

                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(index);

                }

            } finally {

                cursor.close();

            }
        }

        if (result == null) {
            result = uri.getPath();
        }

        return result;
    }

    // ===============================
    // IMPORT CSV
    // ===============================

    private void importCSV() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            try {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getContentResolver().openInputStream(selectedFileUri))
                );

                String line;

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                while ((line = reader.readLine()) != null) {

                    String[] columns = line.split(",");

                    if (columns.length < 3) continue;

                    String date = columns[0];
                    double amount = Double.parseDouble(columns[1]);
                    String note = columns[2];

                    TransactionEntity transaction =
                            new TransactionEntity(date, "00:00", amount, note);

                    transaction.tx_id = UUID.randomUUID().toString();
                    transaction.month = date.substring(0, 7);

                    db.transactionDao().insert(transaction);
                }

                runOnUiThread(() ->
                        Toast.makeText(this, "Import successful", Toast.LENGTH_LONG).show()
                );

            } catch (Exception e) {

                e.printStackTrace();

                runOnUiThread(() ->
                        Toast.makeText(this, "Import failed", Toast.LENGTH_LONG).show()
                );
            }
        });
    }
}
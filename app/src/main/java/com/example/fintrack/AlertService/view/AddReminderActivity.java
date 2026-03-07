package com.example.fintrack.AlertService.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.AlertService.data.ReminderRepository;
import com.example.fintrack.AlertService.usecase.CreateReminderUseCase;
import com.example.fintrack.core.database.DatabaseHelper;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    EditText etTitle, etAmount, etDate;
    Spinner spPeriod;
    Button btnSave;

    int selectedDay = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        spPeriod = findViewById(R.id.spPeriod);
        btnSave = findViewById(R.id.btnSave);

        // Spinner dữ liệu
        String[] periods = {"MONTHLY", "YEARLY"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                periods
        );

        spPeriod.setAdapter(adapter);

        // mở calendar khi click date
        etDate.setOnClickListener(v -> openCalendar());

        btnSave.setOnClickListener(v -> saveReminder());
    }

    private void openCalendar() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    selectedDay = dayOfMonth;

                    String date =
                            dayOfMonth + "/" +
                                    (month + 1) + "/" +
                                    year;

                    etDate.setText(date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void saveReminder() {

        String title = etTitle.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();

        if(title.isEmpty() || amountStr.isEmpty() || selectedDay == -1){
            Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        String period = spPeriod.getSelectedItem().toString();

        ReminderRepository repo =
                new ReminderRepository(
                        DatabaseHelper.getDB(this));

        CreateReminderUseCase create =
                new CreateReminderUseCase(repo);

        create.execute(title, amount, selectedDay, period);

        Toast.makeText(this,"Reminder created!",Toast.LENGTH_SHORT).show();

        finish();
    }
}
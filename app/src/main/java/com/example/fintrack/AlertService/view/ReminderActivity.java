package com.example.fintrack.AlertService.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.R;
import com.example.fintrack.AlertService.data.ReminderRepository;
import com.example.fintrack.AlertService.entity.Reminder;
import com.example.fintrack.AlertService.usecase.CheckReminderUseCase;
import com.example.fintrack.core.database.DatabaseHelper;

import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    Button btnAddReminder;
    RecyclerView recyclerView;

    ReminderRepository repo;
    ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        btnAddReminder = findViewById(R.id.btnAddReminder);
        recyclerView = findViewById(R.id.recyclerReminder);

        repo = new ReminderRepository(DatabaseHelper.getDB(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadReminders();

        btnAddReminder.setOnClickListener(v -> {

            Intent intent = new Intent(
                    ReminderActivity.this,
                    AddReminderActivity.class);

            startActivity(intent);
        });

        checkReminders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReminders(); // reload khi quay lại
    }

    private void loadReminders(){

        List<Reminder> list = repo.findAll();

        adapter = new ReminderAdapter(list);

        recyclerView.setAdapter(adapter);
    }

    private void checkReminders(){

        CheckReminderUseCase check =
                new CheckReminderUseCase(repo);

        check.execute(this);
    }
}
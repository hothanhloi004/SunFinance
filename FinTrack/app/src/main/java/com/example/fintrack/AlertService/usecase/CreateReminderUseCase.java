package com.example.fintrack.AlertService.usecase;

import com.example.fintrack.AlertService.data.ReminderRepository;
import com.example.fintrack.AlertService.entity.Reminder;

import java.util.UUID;

public class CreateReminderUseCase {

    private final ReminderRepository repo;

    public CreateReminderUseCase(ReminderRepository repo) {
        this.repo = repo;
    }

    public void execute(String title,
                        double amount,
                        int day,
                        String period) {

        Reminder r = new Reminder();

        r.id = UUID.randomUUID().toString();
        r.title = title;
        r.amount = amount;
        r.dueDay = day;
        r.period = period;
        r.enabled = true;

        repo.save(r);
    }
}
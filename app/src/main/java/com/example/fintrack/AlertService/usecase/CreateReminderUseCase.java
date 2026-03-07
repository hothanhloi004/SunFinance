package com.example.fintrack.AlertService.usecase;

import com.example.fintrack.AlertService.data.ReminderRepository;
import com.example.fintrack.AlertService.entity.Reminder;

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

        r.title = title;
        r.amount = amount;
        r.dueDay = day;
        r.period = period;

        // bật nhắc nhở mặc định
        r.enabled = true;

        repo.save(r);
    }
}
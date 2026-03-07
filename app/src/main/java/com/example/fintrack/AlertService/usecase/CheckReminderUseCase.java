package com.example.fintrack.AlertService.usecase;

import android.content.Context;

import com.example.fintrack.AlertService.data.ReminderRepository;
import com.example.fintrack.AlertService.entity.Reminder;
import com.example.fintrack.AlertService.service.ReminderDomainService;
import com.example.fintrack.NotificationService.service.NotificationHelper;

import java.util.List;

public class CheckReminderUseCase {

    private final ReminderRepository repo;

    public CheckReminderUseCase(ReminderRepository repo) {
        this.repo = repo;
    }

    public void execute(Context context) {

        ReminderDomainService domain =
                new ReminderDomainService();

        List<Reminder> list = repo.findAll();

        for (Reminder r : list) {

            if (r.enabled &&
                    domain.isDueToday(r.dueDay)) {

                NotificationHelper.send(context,
                        "📅 Bill due: "
                                + r.title +
                                " - "
                                + r.amount
                                + " VND");
            }
        }
    }
}
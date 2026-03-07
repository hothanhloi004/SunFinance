package com.example.fintrack.AlertService.service;

import java.util.Calendar;

public class ReminderDomainService {

    public boolean isDueToday(int dueDay) {

        int today = Calendar.getInstance()
                .get(Calendar.DAY_OF_MONTH);

        return today == dueDay;
    }
}
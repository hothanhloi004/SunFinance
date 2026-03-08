package com.example.fintrack.AlertService.data;

import android.database.Cursor;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.fintrack.AlertService.entity.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderRepository {

    private final SupportSQLiteDatabase db;

    public ReminderRepository(SupportSQLiteDatabase db) {
        this.db = db;
    }

    public void save(Reminder r) {

        db.execSQL(
                "INSERT OR REPLACE INTO reminders VALUES(?,?,?,?,?,?)",
                new Object[]{
                        r.id,
                        r.title,
                        r.amount,
                        r.dueDay,
                        r.period,
                        r.enabled ? 1 : 0
                }
        );
    }

    public List<Reminder> findAll() {

        List<Reminder> list = new ArrayList<>();

        SupportSQLiteQuery query =
                new SimpleSQLiteQuery("SELECT * FROM reminders");

        Cursor c = db.query(query);

        while (c.moveToNext()) {

            Reminder r = new Reminder();

            r.id = c.getString(0);
            r.title = c.getString(1);
            r.amount = c.getDouble(2);
            r.dueDay = c.getInt(3);
            r.period = c.getString(4);
            r.enabled = c.getInt(5) == 1;

            list.add(r);
        }

        c.close();

        return list;
    }
}
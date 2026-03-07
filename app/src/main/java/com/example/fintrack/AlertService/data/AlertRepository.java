package com.example.fintrack.AlertService.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fintrack.AlertService.entity.BudgetAlert;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository {

    private final SQLiteDatabase db;

    public AlertRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public void save(BudgetAlert alert) {

        db.execSQL("INSERT OR REPLACE INTO alerts VALUES(?,?,?,?,?,?,?)",
                new Object[]{
                        alert.id,
                        alert.category,
                        alert.limitAmount,
                        alert.spent,
                        alert.period,
                        alert.threshold,
                        alert.triggered ? 1 : 0
                });
    }

    public void updateSpent(String category, double amount) {
        db.execSQL("UPDATE alerts SET spent = spent + ? WHERE category=?",
                new Object[]{amount, category});
    }

    public void updateTrigger(String id, boolean value) {
        db.execSQL("UPDATE alerts SET is_triggered=? WHERE alert_id=?",
                new Object[]{value ? 1 : 0, id});
    }

    public List<BudgetAlert> findAll() {

        List<BudgetAlert> list = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM alerts", null);

        while (c.moveToNext()) {

            BudgetAlert a = new BudgetAlert();
            a.id = c.getString(0);
            a.category = c.getString(1);
            a.limitAmount = c.getDouble(2);
            a.spent = c.getDouble(3);
            a.period = c.getString(4);
            a.threshold = c.getDouble(5);
            a.triggered = c.getInt(6) == 1;

            list.add(a);
        }

        c.close();
        return list;
    }
}
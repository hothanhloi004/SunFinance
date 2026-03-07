package com.example.fintrack.AnalyticService.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fintrack.AnalyticService.entity.AnalyticsData;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsRepository {

    private SQLiteDatabase db;

    public AnalyticsRepository(SQLiteDatabase db){
        this.db = db;
    }

    public List<AnalyticsData> getAnalyticsData(){

        List<AnalyticsData> list = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT category, SUM(amount) FROM transactions WHERE type='EXPENSE' GROUP BY category",
                null
        );

        double total = 0;

        List<String> cats = new ArrayList<>();
        List<Double> amts = new ArrayList<>();

        while(c.moveToNext()){
            String cat = c.getString(0);
            double amt = c.getDouble(1);

            cats.add(cat);
            amts.add(amt);
            total += amt;
        }

        c.close();

        for(int i=0;i<cats.size();i++){

            int percent = (int)((amts.get(i)/total)*100);

            list.add(
                    new AnalyticsData(
                            cats.get(i),
                            amts.get(i),
                            percent
                    )
            );
        }

        return list;
    }
}
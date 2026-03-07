package com.example.fintrack.AnalyticService.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.AnalyticService.data.AnalyticsRepository;
import com.example.fintrack.AnalyticService.entity.AnalyticsData;
import com.example.fintrack.AnalyticService.service.AnalyticsDomainService;
import com.example.fintrack.R;
import com.example.fintrack.core.database.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    PieChart pieChart;
    BarChart barChart;
    RecyclerView recyclerView;

    TextView txtInsight, txtBalance;
    Button btnLimit;

    AnalyticsRepository repo;
    AnalyticsDomainService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        recyclerView = findViewById(R.id.recyclerCategory);
        txtInsight = findViewById(R.id.txtInsight);
        txtBalance = findViewById(R.id.txtBalance);

        // NEW BUTTON
        btnLimit = findViewById(R.id.btnLimit);

        // Repository
        repo = new AnalyticsRepository(
                DatabaseHelper.getDB(this)
        );

        // Domain Service
        service = new AnalyticsDomainService(repo);

        // lấy data
        List<AnalyticsData> list = service.getAnalytics();

        // setup chart
        setupPieChart(list);
        setupBarChart(list);

        // recycler category
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategoryAdapter(list));

        // AI Insight
        txtInsight.setText(service.generateInsight(list));

        // Balance
        calculateBalance();

        // Open Trend Report Screen
        btnLimit.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AnalyticsActivity.this,
                    TrendReportActivity.class
            );

            startActivity(intent);

        });
    }

    private void setupPieChart(List<AnalyticsData> list){

        List<PieEntry> entries = new ArrayList<>();

        for(AnalyticsData d : list){
            entries.add(new PieEntry(d.getPercent(), d.getCategory()));
        }

        PieDataSet set = new PieDataSet(entries,"");

        set.setColors(
                Color.parseColor("#2ECC71"),
                Color.parseColor("#F39C12"),
                Color.parseColor("#3498DB"),
                Color.parseColor("#9B59B6"),
                Color.parseColor("#E74C3C")
        );

        set.setValueTextSize(12f);

        PieData data = new PieData(set);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }

    private void setupBarChart(List<AnalyticsData> list){

        List<BarEntry> entries = new ArrayList<>();

        int index = 0;

        for(AnalyticsData d : list){
            entries.add(new BarEntry(index++, (float)d.getAmount()));
        }

        BarDataSet set = new BarDataSet(entries,"Expenses");

        set.setColor(Color.parseColor("#1ABC9C"));
        set.setValueTextSize(12f);

        BarData data = new BarData(set);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }

    private void calculateBalance(){

        double income = 0;
        double expense = 0;

        var db = DatabaseHelper.getDB(this);

        var cursor = db.rawQuery(
                "SELECT type, amount FROM transactions",
                null
        );

        while(cursor.moveToNext()){

            String type = cursor.getString(0);
            double amount = cursor.getDouble(1);

            if(type.equals("INCOME")){
                income += amount;
            }else{
                expense += amount;
            }
        }

        cursor.close();

        double balance = income - expense;

        txtBalance.setText("Balance: " + balance + " VND");
    }
}
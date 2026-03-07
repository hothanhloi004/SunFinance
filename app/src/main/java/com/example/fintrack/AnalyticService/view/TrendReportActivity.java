package com.example.fintrack.AnalyticService.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.TransactionRepository;
import com.example.fintrack.core.database.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrendReportActivity extends AppCompatActivity {

    BarChart chart;
    TextView txtSaving;
    LinearLayout layoutBreakdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_report);

        chart = findViewById(R.id.chartIncome);
        txtSaving = findViewById(R.id.txtSaving);
        layoutBreakdown = findViewById(R.id.layoutBreakdown);

        loadChart();
        loadBreakdown();
    }

    private void loadChart(){

        TransactionRepository repo =
                new TransactionRepository(DatabaseHelper.getDB(this));

        List<BarEntry> income = new ArrayList<>();
        List<BarEntry> expense = new ArrayList<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for(int month=1; month<=6; month++){

            double i = repo.getIncomeByMonth(month);
            double e = repo.getExpenseByMonth(month);

            totalIncome += i;
            totalExpense += e;

            income.add(new BarEntry(month, (float)i));
            expense.add(new BarEntry(month, (float)e));
        }

        double saving = totalIncome - totalExpense;

        txtSaving.setText(
                "VND " + NumberFormat.getInstance().format(saving)
        );

        BarDataSet incomeSet = new BarDataSet(income,"Income");
        incomeSet.setColor(0xFF2ECC71);

        BarDataSet expenseSet = new BarDataSet(expense,"Expense");
        expenseSet.setColor(0xFFBDC3C7);

        BarData data = new BarData(incomeSet,expenseSet);

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }

    private void loadBreakdown(){

        TransactionRepository repo =
                new TransactionRepository(DatabaseHelper.getDB(this));

        Map<String,double[]> map = new LinkedHashMap<>();

        Calendar cal = Calendar.getInstance();

        for(int month=1; month<=6; month++){

            double income = repo.getIncomeByMonth(month);
            double expense = repo.getExpenseByMonth(month);

            cal.set(Calendar.MONTH,month-1);

            String monthName =
                    new SimpleDateFormat("MMMM").format(cal.getTime());

            map.put(monthName,new double[]{income,expense});
        }

        for(String m : map.keySet()){

            double income = map.get(m)[0];
            double expense = map.get(m)[1];

            if(income==0 && expense==0) continue;

            TextView tv = new TextView(this);

            tv.setText(
                    m + "     "
                            + NumberFormat.getInstance().format(income)
                            + "     "
                            + NumberFormat.getInstance().format(expense)
            );

            tv.setPadding(10,10,10,10);

            layoutBreakdown.addView(tv);
        }
    }
}
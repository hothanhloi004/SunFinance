package com.example.fintrack.AnalyticService.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
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

        new Thread(() -> {

            FintrackDatabase db =
                    FintrackDatabase.getInstance(getApplicationContext());

            List<TransactionEntity> list =
                    db.transactionDao().getAll();

            List<BarEntry> income = new ArrayList<>();
            List<BarEntry> expense = new ArrayList<>();

            double totalIncome = 0;
            double totalExpense = 0;

            Calendar cal = Calendar.getInstance();

            for(int month = 1; month <= 6; month++){

                double i = 0;
                double e = 0;

                for(TransactionEntity t : list){

                    cal.setTime(new Date(t.tx_date));

                    int m = cal.get(Calendar.MONTH) + 1;

                    if(m == month){

                        if("INCOME".equals(t.tx_type_id)){
                            i += t.amount;
                        }else{
                            e += t.amount;
                        }
                    }
                }

                totalIncome += i;
                totalExpense += e;

                income.add(new BarEntry(month,(float)i));
                expense.add(new BarEntry(month,(float)e));
            }

            double saving = totalIncome - totalExpense;

            runOnUiThread(() -> {

                txtSaving.setText(
                        "VND " + NumberFormat.getInstance().format(saving)
                );

                BarDataSet incomeSet =
                        new BarDataSet(income,"Income");

                incomeSet.setColor(0xFF2ECC71);

                BarDataSet expenseSet =
                        new BarDataSet(expense,"Expense");

                expenseSet.setColor(0xFFBDC3C7);

                BarData data =
                        new BarData(incomeSet,expenseSet);

                chart.setData(data);
                chart.getDescription().setEnabled(false);
                chart.invalidate();
            });

        }).start();
    }

    private void loadBreakdown(){

        new Thread(() -> {

            FintrackDatabase db =
                    FintrackDatabase.getInstance(getApplicationContext());

            List<TransactionEntity> list =
                    db.transactionDao().getAll();

            Map<String,double[]> map =
                    new LinkedHashMap<>();

            Calendar cal = Calendar.getInstance();

            for(int month=1; month<=6; month++){

                double income = 0;
                double expense = 0;

                for(TransactionEntity t : list){

                    cal.setTime(new Date(t.tx_date));

                    int m = cal.get(Calendar.MONTH) + 1;

                    if(m == month){

                        if("INCOME".equals(t.tx_type_id)){
                            income += t.amount;
                        }else{
                            expense += t.amount;
                        }
                    }
                }

                cal.set(Calendar.MONTH,month-1);

                String monthName =
                        new SimpleDateFormat("MMMM",Locale.getDefault())
                                .format(cal.getTime());

                map.put(monthName,new double[]{income,expense});
            }

            runOnUiThread(() -> {

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

            });

        }).start();
    }
}
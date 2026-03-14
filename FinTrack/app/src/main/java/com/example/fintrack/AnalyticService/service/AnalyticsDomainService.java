package com.example.fintrack.AnalyticService.service;

import com.example.fintrack.AnalyticService.data.AnalyticsRepository;
import com.example.fintrack.AnalyticService.entity.AnalyticsData;

import java.text.NumberFormat;
import java.util.List;

public class AnalyticsDomainService {

    private AnalyticsRepository repository;

    public AnalyticsDomainService(AnalyticsRepository repository) {
        this.repository = repository;
    }

    public List<AnalyticsData> getAnalytics() {
        return repository.getAnalyticsData();
    }

    public String generateInsight(List<AnalyticsData> data){

        if(data == null || data.isEmpty()){
            return "Chưa có dữ liệu chi tiêu.";
        }

        double total = 0;
        AnalyticsData highest = null;

        for(AnalyticsData d : data){

            total += d.getAmount();

            if(highest == null || d.getAmount() > highest.getAmount()){
                highest = d;
            }
        }

        if(total == 0){
            return "Chưa có chi tiêu.";
        }

        double percent = highest.getAmount() / total;

        // ⚠ nếu 1 category chiếm > 50%
        if(percent > 0.5){

            return "⚠ Bạn đang chi quá nhiều cho "
                    + highest.getCategory()
                    + " (" + (int)(percent * 100) + "%)";
        }

        // ⚠ nếu category > 3 triệu
        if(highest.getAmount() > 3000000){

            return "⚠ " + highest.getCategory()
                    + " đang chi khá nhiều: "
                    + NumberFormat.getInstance().format(highest.getAmount())
                    + " VND";
        }

        // ⚠ nếu tổng chi > 5 triệu
        if(total > 5000000){

            return "⚠ Tổng chi tiêu tháng này khá cao: "
                    + NumberFormat.getInstance().format(total)
                    + " VND";
        }

        return "Chi tiêu của bạn đang ổn định.";
    }
}
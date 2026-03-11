package com.example.fintrack.AlertService.data;

import com.example.fintrack.AlertService.entity.BudgetAlert;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository {

    private static AlertRepository instance;

    private final List<BudgetAlert> alerts = new ArrayList<>();

    private AlertRepository(){}

    public static synchronized AlertRepository getInstance(){
        if(instance == null){
            instance = new AlertRepository();
        }
        return instance;
    }

    public List<BudgetAlert> findAll(){
        return alerts;
    }

    public void add(BudgetAlert alert){
        alerts.add(alert);
    }

    public void update(BudgetAlert alert){

        for(int i=0;i<alerts.size();i++){

            if(alerts.get(i).id.equals(alert.id)){
                alerts.set(i, alert);
                return;
            }
        }
    }

    public void delete(BudgetAlert alert){
        alerts.remove(alert);
    }

    public void clear(){
        alerts.clear();
    }
}
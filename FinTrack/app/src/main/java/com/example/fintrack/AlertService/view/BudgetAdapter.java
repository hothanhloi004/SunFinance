package com.example.fintrack.AlertService.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.R;
import com.example.fintrack.AlertService.entity.BudgetAlert;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private List<BudgetAlert> list;

    public BudgetAdapter(List<BudgetAlert> list){
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategory,tvAmount;
        ProgressBar pb;

        public ViewHolder(View v){
            super(v);

            tvCategory = v.findViewById(R.id.tvCategory);
            tvAmount = v.findViewById(R.id.tvAmount);
            pb = v.findViewById(R.id.pbBudget);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h,int i){

        BudgetAlert b = list.get(i);

        h.tvCategory.setText(b.categoryName);

        h.tvAmount.setText(
                String.format("%,.0f / %,.0f VND",
                        b.spent,
                        b.limitAmount)
        );

        int percent = 0;

        if(b.limitAmount>0){
            percent = (int)((b.spent/b.limitAmount)*100);
        }

        h.pb.setProgress(percent);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
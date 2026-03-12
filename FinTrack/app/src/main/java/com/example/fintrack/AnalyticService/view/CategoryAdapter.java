package com.example.fintrack.AnalyticService.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.AnalyticService.entity.AnalyticsData;
import com.example.fintrack.R;

import java.text.NumberFormat;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    List<AnalyticsData> list;

    public CategoryAdapter(List<AnalyticsData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AnalyticsData data = list.get(position);

        holder.txtCategory.setText(data.getCategory());

        holder.txtAmount.setText(
                NumberFormat.getInstance().format(data.getAmount()) + " VND"
        );

        holder.txtPercent.setText(data.getPercent()+"%");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategory,txtAmount,txtPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtPercent = itemView.findViewById(R.id.txtPercent);
        }
    }
}
package com.example.fintrack.AlertService.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.R;
import com.example.fintrack.AlertService.entity.Reminder;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    List<Reminder> list;

    public ReminderAdapter(List<Reminder> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Reminder r = list.get(position);

        holder.txtTitle.setText(r.getTitle());

        holder.txtInfo.setText(
                "Due day: " + r.getDay() + " • " + r.getAmount() + " VND"
        );

        holder.swReminder.setChecked(r.isEnabled());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtInfo;
        Switch swReminder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            swReminder = itemView.findViewById(R.id.swReminder);
        }
    }
}
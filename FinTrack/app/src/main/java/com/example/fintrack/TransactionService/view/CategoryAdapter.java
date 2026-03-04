package com.example.fintrack.TransactionService.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.entity.CategoryEntity;

import java.util.List;

public class CategoryAdapter
        extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public interface Listener {
        void onEdit(CategoryEntity c);
        void onDelete(CategoryEntity c);
    }

    private List<CategoryEntity> list;
    private final Listener listener;

    public CategoryAdapter(List<CategoryEntity> list, Listener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateData(List<CategoryEntity> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder h, int pos) {

        CategoryEntity c = list.get(pos);
        h.txtIcon.setText(c.icon);
        h.txtName.setText(c.name + " (" + c.tx_type_id + ")");

        h.btnEdit.setOnClickListener(v -> listener.onEdit(c));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(c));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIcon, txtName;
        Button btnEdit, btnDelete;

        ViewHolder(View v) {
            super(v);
            txtIcon = v.findViewById(R.id.txtIcon);
            txtName = v.findViewById(R.id.txtName);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}

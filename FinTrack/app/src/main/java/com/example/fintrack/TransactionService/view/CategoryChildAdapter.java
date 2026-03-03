package com.example.fintrack.TransactionService.view;

import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.entity.CategoryEntity;
import java.util.List;

public class CategoryChildAdapter
        extends RecyclerView.Adapter<CategoryChildAdapter.VH> {

    public interface Listener {
        void onEdit(CategoryEntity c);
        void onDelete(CategoryEntity c);
        void onClick(CategoryEntity c);
    }

    private List<CategoryEntity> list;
    private final Listener listener;

    public CategoryChildAdapter(List<CategoryEntity> list, Listener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void update(List<CategoryEntity> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        View view = LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_category_child, p, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        CategoryEntity c = list.get(pos);
        h.name.setText(c.name);
        h.btnEdit.setOnClickListener(v -> listener.onEdit(c));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(c));
        h.itemView.setOnClickListener(v -> listener.onClick(c));

    }

    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name;
        Button btnEdit, btnDelete;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.txtChildName);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}

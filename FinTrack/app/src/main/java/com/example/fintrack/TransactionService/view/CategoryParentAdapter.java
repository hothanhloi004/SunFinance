package com.example.fintrack.TransactionService.view;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.entity.CategoryEntity;
import java.util.List;

public class CategoryParentAdapter
        extends RecyclerView.Adapter<CategoryParentAdapter.VH> {

    public interface Listener {
        void onClick(CategoryEntity parent);
        void onLongClick(CategoryEntity parent);
    }


    private List<CategoryEntity> list;
    private final Listener listener;

    public CategoryParentAdapter(List<CategoryEntity> list, Listener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void update(List<CategoryEntity> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        View view = LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_category_parent, p, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        CategoryEntity c = list.get(pos);
        h.icon.setText(c.icon);
        h.name.setText(c.name);

        h.itemView.setOnClickListener(v -> listener.onClick(c));

        h.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(c);
            return true;
        });
    }


    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView icon, name;
        VH(View v) {
            super(v);
            icon = v.findViewById(R.id.txtIcon);
            name = v.findViewById(R.id.txtName);
        }
    }
}

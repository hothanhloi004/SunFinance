package com.example.fintrack.AccountService.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fintrack.AccountService.model.AccountTypeEntity;
import com.example.fintrack.R;
import java.util.List;

public class WalletTypeAdapter extends RecyclerView.Adapter<WalletTypeAdapter.ViewHolder> {

    private List<AccountTypeEntity> typeList;
    private OnTypeDeleteListener deleteListener;

    public interface OnTypeDeleteListener {
        void onDelete(String typeId, int position);
    }

    public WalletTypeAdapter(List<AccountTypeEntity> typeList, OnTypeDeleteListener deleteListener) {
        this.typeList = typeList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountTypeEntity type = typeList.get(position);

        holder.tvId.setText(type.typeid);
        holder.tvName.setText(type.name);
        holder.tvDesc.setText(type.description);

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(type.typeid, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList != null ? typeList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvDesc;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvTypeId);
            tvName = itemView.findViewById(R.id.tvTypeName);
            tvDesc = itemView.findViewById(R.id.tvTypeDesc);
            btnDelete = itemView.findViewById(R.id.btnDeleteType);
        }
    }
}
package com.example.fintrack.TransactionService.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fintrack.R;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.domain.usecase.DeleteTransactionUseCase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.content.Intent;
import com.example.fintrack.AccountService.api.AccountApiImpl;
public class TransactionOptionsBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_TX_ID = "tx_id";

    public static TransactionOptionsBottomSheet newInstance(String txId) {
        TransactionOptionsBottomSheet sheet = new TransactionOptionsBottomSheet();
        Bundle b = new Bundle();
        b.putString(ARG_TX_ID, txId);
        sheet.setArguments(b);
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View v = inflater.inflate(
                R.layout.bottom_sheet_transaction_options,
                container,
                false
        );

        String txId = getArguments().getString(ARG_TX_ID);

        TextView btnEdit = v.findViewById(R.id.btnEdit);
        TextView btnDelete = v.findViewById(R.id.btnDelete);

        // ✏️ EDIT
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(
                    requireContext(),
                    UpdateDeleteTransactionActivity.class
            );
            intent.putExtra("TX_ID", txId);
            startActivity(intent);
            dismiss();
        });


        // 🗑 DELETE
        btnDelete.setOnClickListener(view -> {
            new Thread(() -> {

                FintrackDatabase db =
                        FintrackDatabase.getInstance(requireContext());

                new DeleteTransactionUseCase(
                        db.transactionDao(),
                        new AccountApiImpl(requireContext())
                ).execute(txId);

                requireActivity().runOnUiThread(() -> {

                    Toast.makeText(
                            getContext(),
                            "Đã xoá giao dịch",
                            Toast.LENGTH_SHORT
                    ).show();

                    dismiss();

                    // refresh History
                    requireActivity().recreate();
                });

            }).start();
        });

        return v;
    }
}

package com.example.fintrack.TransactionService.view;

import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

public class TransactionUiModel {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public int type;
    public String headerTitle;
    public TransactionEntity tx;

    // Header
    public static TransactionUiModel header(String title) {
        TransactionUiModel m = new TransactionUiModel();
        m.type = TYPE_HEADER;
        m.headerTitle = title;
        return m;
    }

    // Item
    public static TransactionUiModel item(TransactionEntity tx) {
        TransactionUiModel m = new TransactionUiModel();
        m.type = TYPE_ITEM;
        m.tx = tx;
        return m;
    }
}

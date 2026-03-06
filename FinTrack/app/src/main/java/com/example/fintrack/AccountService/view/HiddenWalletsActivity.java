package com.example.fintrack.AccountService.view;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fintrack.AccountService.data.AccountRepository;
import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.R;
import java.util.ArrayList;
import java.util.List;

public class HiddenWalletsActivity extends AppCompatActivity {
    private AccountAdapter adapter;
    private List<AccountEntity> hiddenList = new ArrayList<>();
    private AccountRepository repo = AccountRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_wallets);

        findViewById(R.id.btnBackHidden).setOnClickListener(v -> finish());

        RecyclerView rvHidden = findViewById(R.id.rvHiddenWallets);
        adapter = new AccountAdapter(hiddenList);
        rvHidden.setLayoutManager(new LinearLayoutManager(this));
        rvHidden.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hiddenList.clear();
        for (AccountEntity acc : repo.getMockData()) {
            if (AccountEntity.STATUS_HIDDEN.equals(acc.status)) {
                hiddenList.add(acc);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
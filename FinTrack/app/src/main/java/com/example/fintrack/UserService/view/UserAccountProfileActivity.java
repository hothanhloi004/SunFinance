package com.example.fintrack.UserService.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fintrack.UserService.data.UserRepository;
import com.example.fintrack.UserService.entity.User;


import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;

public class UserAccountProfileActivity extends AppCompatActivity {

    ImageView btnBack, btnSetting;
    TextView txtName, txtEmail, txtStatus;
    Button btnLogout;

    LinearLayout itemPersonalInfo, itemSecurity, itemLanguage;
    LinearLayout itemNotification, itemDefaultCurrency, itemHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_profile);

        // ===== INIT VIEW =====
        btnBack = findViewById(R.id.btnBack);
        btnSetting = findViewById(R.id.btnSetting);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtStatus = findViewById(R.id.txtStatus);

        btnLogout = findViewById(R.id.btnLogout);

        itemPersonalInfo = findViewById(R.id.itemPersonalInfo);
        itemSecurity = findViewById(R.id.itemSecurity);
        itemLanguage = findViewById(R.id.itemLanguage);
        itemNotification = findViewById(R.id.itemNotification);
        itemDefaultCurrency = findViewById(R.id.itemDefaultCurrency);
        itemHelp = findViewById(R.id.itemHelp);

        // ===== MOCK USER DATA (THEO CSDL MẪU) =====
        loadMockUser();


        // ===== EVENTS =====
        btnBack.setOnClickListener(v -> finish());

        btnSetting.setOnClickListener(v ->
                Toast.makeText(this, "Settings (coming soon)", Toast.LENGTH_SHORT).show()
        );

        itemPersonalInfo.setOnClickListener(v ->
                Toast.makeText(this, "Personal Info", Toast.LENGTH_SHORT).show()
        );

        itemSecurity.setOnClickListener(v ->
                Toast.makeText(this, "Security Settings", Toast.LENGTH_SHORT).show()
        );

        itemLanguage.setOnClickListener(v ->
                Toast.makeText(this, "Language Settings", Toast.LENGTH_SHORT).show()
        );

        itemNotification.setOnClickListener(v ->
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        );

        itemDefaultCurrency.setOnClickListener(v ->
                Toast.makeText(this, "Default Currency: VND", Toast.LENGTH_SHORT).show()
        );

        itemHelp.setOnClickListener(v ->
                Toast.makeText(this, "Help Center", Toast.LENGTH_SHORT).show()
        );

        btnLogout.setOnClickListener(v -> logout());
    }

    // ===== MOCK DATA/FAKE DATA =====
    private void loadMockUser() {
        User user = UserRepository.getInstance().getCurrentUser();

        if (user == null) {
            finish(); // chưa login
            return;
        }

        txtName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());
        txtStatus.setText(user.getStatus());
    }


    // ===== LOGOUT =====
    private void logout() {

        UserRepository.getInstance().logout();

        getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

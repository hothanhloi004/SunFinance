package com.example.fintrack.UserService.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.UserService.data.UserRepository;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 5;
                progressBar.setProgress(progress);

                if (progress < 100) {
                    handler.postDelayed(this, 80);
                } else {

                    checkAutoLogin();   //  THÊM DÒNG NÀY

                }
            }
        }, 80);
    }

    private void checkAutoLogin() {

        SharedPreferences prefs =
                getSharedPreferences("USER_SESSION", MODE_PRIVATE);

        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String email = prefs.getString("email", null);

        if (isLoggedIn && email != null) {

            UserRepository repo = UserRepository.getInstance();
            repo.restoreSession(email);

            if (repo.isLoggedIn()) {
                startActivity(new Intent(this, UserAccountProfileActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }
}
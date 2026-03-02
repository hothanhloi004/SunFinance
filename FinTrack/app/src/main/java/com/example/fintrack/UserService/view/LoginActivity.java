package com.example.fintrack.UserService.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fintrack.UserService.data.UserRepository;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;


public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtRegister, txtForgot;
    ImageView btnBack;
    boolean isPasswordVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgot = findViewById(R.id.txtForgot);
        btnBack = findViewById(R.id.btnBack);
        edtPassword.setOnTouchListener((v, event) -> {

            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {

                if (edtPassword.getCompoundDrawables()[2] != null) {

                    int drawableWidth = edtPassword
                            .getCompoundDrawables()[2]
                            .getBounds()
                            .width();

                    if (event.getRawX() >= (edtPassword.getRight() - drawableWidth)) {

                        if (isPasswordVisible) {

                            // Ẩn mật khẩu
                            edtPassword.setInputType(
                                    android.text.InputType.TYPE_CLASS_TEXT |
                                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                            );

                            isPasswordVisible = false;

                        } else {

                            // Hiện mật khẩu
                            edtPassword.setInputType(
                                    android.text.InputType.TYPE_CLASS_TEXT |
                                            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            );

                            isPasswordVisible = true;
                        }

                        edtPassword.setSelection(edtPassword.getText().length());

                        return true;
                    }
                }
            }
            return false;
        });

        btnLogin.setOnClickListener(v -> {

            UserRepository repo = UserRepository.getInstance();

            // 1. Check bị khóa chưa
            if (repo.isLocked()) {
                Toast.makeText(
                        this,
                        "Bạn đã nhập sai quá 3 lần. Vui lòng reset mật khẩu",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            // 2. Thực hiện login
            boolean success = UserRepository
                    .getInstance()
                    .login(
                            edtUsername.getText().toString().trim(),
                            edtPassword.getText().toString().trim()
                    );

            // 3. Login thất bại
            if (!success) {
                int remain = 3 - repo.getLoginFailCount();
                Toast.makeText(
                        this,
                        "Sai email hoặc mật khẩu. Còn " + remain + " lần thử",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // 4. Login thành công

            // Lưu session
            getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("email", edtUsername.getText().toString().trim())
                    .apply();

            startActivity(new Intent(this, UserAccountProfileActivity.class));
            finish();
        });


        btnBack.setOnClickListener(v -> finish());

        txtRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        txtForgot.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class))
        );
    }


}

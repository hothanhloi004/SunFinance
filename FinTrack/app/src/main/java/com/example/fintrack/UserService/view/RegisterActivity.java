package com.example.fintrack.UserService.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.UserService.data.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    Button btnRegister;
    TextView txtLogin;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        btnBack = findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(v -> register());
        btnBack.setOnClickListener(v -> finish());
        txtLogin.setOnClickListener(v -> finish());
    }

    private void register() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        // 1. validate rỗng
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 2. Validate username ≥ 8 ký tự
        if (username.length() < 8) {
            Toast.makeText(this,
                    "Username phải tối thiểu 8 ký tự",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 3. validate password >= 8, có chữ và số
        if (!isValidPassword(password)) {
            Toast.makeText(this,
                    "Mật khẩu phải ≥ 8 ký tự, gồm chữ và số",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 4. validate email
        if (!isValidGmail(email)) {
            Toast.makeText(this,
                    "Email phải đúng định dạng @gmail.com",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 3. register
        boolean success = UserRepository
                .getInstance()
                .register(username, email, password);


        if (!success) {
            Toast.makeText(this,
                    "Email đã tồn tại",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this,
                "Tạo tài khoản thành công",
                Toast.LENGTH_SHORT).show();

        finish(); // quay về Login / Home (đã auto login)
    }

    // ===== PASSWORD VALIDATION =====
    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasLetter && hasDigit;
    }
    // Validate email đúng dạng @gmail.com
    private boolean isValidGmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$");
    }
}

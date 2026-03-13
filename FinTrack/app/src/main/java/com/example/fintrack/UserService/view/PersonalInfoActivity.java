package com.example.fintrack.UserService.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.UserService.data.UserRepository;
import com.example.fintrack.UserService.data.entity.UserEntity;

public class PersonalInfoActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail, etStatus;
    Button btnUpdate, btnCancel;
    ImageView btnBack, imgAvatar;

    UserRepository repo;
    UserEntity currentUser;

    Uri avatarUri;

    // ===== Activity Result Launcher =====
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etStatus = findViewById(R.id.etStatus);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        btnBack = findViewById(R.id.btnBack);
        imgAvatar = findViewById(R.id.imgAvatar);

        repo = new UserRepository(this);

        // ===== Launcher nhận ảnh =====
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        avatarUri = result.getData().getData();

                        try {
                            getContentResolver().takePersistableUriPermission(
                                    avatarUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        imgAvatar.setImageURI(avatarUri);
                    }
                });

        loadUser();

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        btnUpdate.setOnClickListener(v -> updateUser());

        imgAvatar.setOnClickListener(v -> openGallery());
    }

    private void loadUser() {

        currentUser = repo.getCurrentUser();

        if (currentUser == null) return;

        etEmail.setText(currentUser.email);
        etStatus.setText(currentUser.status);

        etUsername.setHint(currentUser.full_name);

        if (currentUser.avatar != null && !currentUser.avatar.isEmpty()) {
            try {
                Uri uri = Uri.parse(currentUser.avatar);
                imgAvatar.setImageURI(uri);
            } catch (Exception e) {
                imgAvatar.setImageResource(R.drawable.avatar);
            }
        } else {
            imgAvatar.setImageResource(R.drawable.avatar);
        }
    }

    private void updateUser() {

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // ===== USERNAME =====

        if (!username.isEmpty()) {

            if (username.length() < 8) {
                Toast.makeText(this,
                        "Tên người dùng phải có ít nhất 8 ký tự",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // CHECK USERNAME TRÙNG
            UserEntity exist = repo.getUserByUsername(username);

            if (exist != null && !exist.user_id.equals(currentUser.user_id)) {

                Toast.makeText(this,
                        "Tên người dùng đã được sử dụng",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            username = currentUser.full_name;
        }

        // ===== PASSWORD =====

        if (!password.isEmpty()) {

            if (password.length() < 8) {
                Toast.makeText(this,
                        "Mật khẩu phải có ít nhất 8 ký tự",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.matches(".*[A-Z].*")) {
                Toast.makeText(this,
                        "Mật khẩu phải có ít nhất 1 chữ cái viết hoa",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            password = currentUser.password;
        }

        // ===== AVATAR =====

        String avatar = currentUser.avatar;

        if (avatarUri != null) {
            avatar = avatarUri.toString();
        }

        repo.updateUser(currentUser.user_id, username, password, avatar);

        Toast.makeText(this,
                "Cập nhật thông tin thành công",
                Toast.LENGTH_LONG).show();

        finish();
    }

    // ====== MỞ GALLERY ======

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        galleryLauncher.launch(intent);
    }
}
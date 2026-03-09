package com.example.fintrack.UserService.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fintrack.R;
import com.example.fintrack.UserService.data.UserRepository;
import com.example.fintrack.UserService.data.entity.UserEntity;
import com.example.fintrack.UserService.util.LocaleHelper;

public class LanguageActivity extends AppCompatActivity {

    ImageView btnBack;
    RadioGroup radioLanguages;

    String selectedLanguage = "en";

    UserRepository repo;
    UserEntity currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Apply language khi mở activity
        LocaleHelper.applyLanguage(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        btnBack = findViewById(R.id.btnBack);
        radioLanguages = findViewById(R.id.radioLanguages);

        repo = new UserRepository(this);
        currentUser = repo.getCurrentUser();

        loadCurrentLanguage();

        radioLanguages.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbSystem) {
                selectedLanguage = "system";
                Toast.makeText(this, getString(R.string.system_language_applied), Toast.LENGTH_SHORT).show();
            }

            else if (checkedId == R.id.rbVietnamese || checkedId == R.id.rbVietnameseAll) {
                selectedLanguage = "vi";
                Toast.makeText(this, getString(R.string.vietnamese_applied), Toast.LENGTH_SHORT).show();
            }

            else if (checkedId == R.id.rbEnglish) {
                selectedLanguage = "en";
                Toast.makeText(this, getString(R.string.english_applied), Toast.LENGTH_SHORT).show();
            }

            else if (checkedId == R.id.rbJapanese) {
                selectedLanguage = "ja";
                Toast.makeText(this, getString(R.string.japanese_applied), Toast.LENGTH_SHORT).show();
            }

            applyLanguage();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void applyLanguage() {

        if (currentUser == null) return;

        String oldLang = currentUser.language;

        if (oldLang != null && oldLang.equals(selectedLanguage)) {
            return;
        }

        // lưu language vào database
        repo.updateLanguage(selectedLanguage);

        // apply locale
        LocaleHelper.applyLanguage(this);

        // restart app stack
        Intent intent = new Intent(this, UserAccountProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    private void loadCurrentLanguage() {

        if (currentUser == null) return;

        String lang = currentUser.language;

        if (lang == null || lang.isEmpty()) {
            lang = "en";
        }

        selectedLanguage = lang;

        if (lang.equals("vi")) {
            radioLanguages.check(R.id.rbVietnamese);
        }
        else if (lang.equals("en")) {
            radioLanguages.check(R.id.rbEnglish);
        }
        else if (lang.equals("ja")) {
            radioLanguages.check(R.id.rbJapanese);
        }
        else {
            radioLanguages.check(R.id.rbSystem);
        }
    }
}
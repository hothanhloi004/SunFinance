package com.example.fintrack.UserService.util;

import android.content.Context;
import android.content.res.Configuration;

import com.example.fintrack.UserService.data.UserRepository;
import com.example.fintrack.UserService.data.entity.UserEntity;

import java.util.Locale;

public class LocaleHelper {

    public static void applyLanguage(Context context) {

        UserRepository repo = new UserRepository(context);
        UserEntity user = repo.getCurrentUser();

        if (user == null || user.language == null) return;

        String lang = user.language;

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        context.getResources().updateConfiguration(
                config,
                context.getResources().getDisplayMetrics()
        );
    }
}

package com.example.fintrack.TransactionService.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

@Database(entities = {TransactionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TransactionDao transactionDao();

    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "fintrack_db"
                    ).fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
package com.example.fintrack.TransactionService.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fintrack.TransactionService.entity.TransactionEntity;

@Database(entities = {TransactionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "fintrack_db"
                    )
                    .allowMainThreadQueries() // DEV MODE
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
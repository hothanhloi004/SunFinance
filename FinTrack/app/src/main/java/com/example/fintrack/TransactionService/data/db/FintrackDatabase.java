package com.example.fintrack.TransactionService.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fintrack.TransactionService.data.dao.AlertDao;
import com.example.fintrack.TransactionService.data.dao.CategoryDao;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.*;
import com.example.fintrack.AccountService.data.AccountDao;
import com.example.fintrack.AccountService.model.AccountEntity;
import com.example.fintrack.AccountService.model.AccountTypeEntity;
import com.example.fintrack.UserService.data.dao.UserDao;//User thêm vào
import com.example.fintrack.UserService.data.entity.UserEntity;// User

@Database(
        entities = {
                TransactionEntity.class,
                AccountEntity.class,
                CategoryEntity.class,
                AlertEntity.class,
                TxTypeEntity.class,
                UserEntity.class,
                AccountTypeEntity.class
        },
        version = 12,
        exportSchema = false
)
public abstract class FintrackDatabase extends RoomDatabase {

    private static volatile FintrackDatabase INSTANCE;

    public abstract TransactionDao transactionDao();
    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();
    public abstract AlertDao alertDao();
    public abstract UserDao userDao(); //User thêm vào

    public static FintrackDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FintrackDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    FintrackDatabase.class,
                                    "fintrack_db_clean"
                            )
                            .allowMainThreadQueries()   // thêm dòng này
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    db.execSQL("INSERT INTO accounts (account_id,user_id,name,type_id,balance,status,created_at) VALUES ('acc001','u001','Ví chính','WALLET',10000000,'ACTIVE','2026')");
                                    db.execSQL("INSERT INTO accounts (account_id,user_id,name,type_id,balance,status,created_at) VALUES ('acc002','u001','Ngân hàng','WALLET',50000000,'ACTIVE','2026')");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
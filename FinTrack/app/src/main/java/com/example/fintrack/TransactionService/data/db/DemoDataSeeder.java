package com.example.fintrack.TransactionService.data.db;

public class DemoDataSeeder {

    public static void seed(FintrackDatabase db) {

        if (db.transactionDao()
                .searchTransactions("u001", null, null, null, null, null)
                .size() > 0) {
            return;
        }

        db.runInTransaction(() -> {

            // ===== TX TYPES =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO tx_types (tx_type_id, name, sign) VALUES " +
                            "('INCOME','Thu nhập',1)," +
                            "('EXPENSE','Chi tiêu',-1)," +
                            "('TRANSFER','Chuyển khoản',0)"
            );

            // ===== USER =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO users (user_id,email,full_name,status,created_at) VALUES " +
                            "('u001','NguyenVanA@gmail.com','Nguyen Van A','ACTIVE',CURRENT_TIMESTAMP)"
            );

            // ===== ACCOUNT TYPES =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO account_types (type_id,name,description) VALUES " +
                            "('WALLET','Ví tiền','Tiền mặt')"
            );

            // ===== ACCOUNTS =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO accounts " +
                            "(account_id,user_id,name,type_id,balance,status,created_at) VALUES " +
                            "('acc001','u001','Ví chính','WALLET',10000000,'ACTIVE',CURRENT_TIMESTAMP)," +
                            "('acc002','u001','Ngân hàng','WALLET',5000000,'ACTIVE',CURRENT_TIMESTAMP)"
            );

            // ===== CATEGORIES =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO categories " +
                            "(category_id,name,tx_type_id,icon,user_id,parent_category_id) VALUES " +
                            "('FOOD','Ăn uống','EXPENSE','🍜','u001',NULL)," +
                            "('MOVE','Di chuyển','EXPENSE','🛵','u001',NULL)," +
                            "('SALARY','Lương','INCOME','💰','u001',NULL)"
            );

            // ===== TRANSACTIONS =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO transactions " +
                            "(tx_id, user_id, tx_type_id, source_account_id, target_account_id, category_id, amount, note, tx_date, created_at, month) VALUES " +
                            "('tx100','u001','INCOME',NULL,'acc002','SALARY',5000000,'Lương tháng 1','2026-01-01',CURRENT_TIMESTAMP,'2026-01')," +
                            "('tx101','u001','EXPENSE','acc001',NULL,'FOOD',50000,'Ăn sáng','2026-01-02',CURRENT_TIMESTAMP,'2026-01')," +
                            "('tx102','u001','TRANSFER','acc001','acc002',NULL,1000000,'Chuyển tiền','2026-01-03',CURRENT_TIMESTAMP,'2026-01')"
            );

            // ===== ALERT (FIX CỘT) =====
            db.getOpenHelper().getWritableDatabase().execSQL(
                    "INSERT OR IGNORE INTO alerts " +
                            "(alert_id,user_id,category_id,monthly_limit,current_spent,threshold,is_triggered) VALUES " +
                            "('alert001','u001','FOOD',2000000,50000,0.9,0)"
            );
        });
    }
}
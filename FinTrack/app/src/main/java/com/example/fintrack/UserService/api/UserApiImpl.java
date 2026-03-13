package com.example.fintrack.UserService.api;

import android.content.Context;

import com.example.fintrack.UserService.data.dao.UserDao;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.UserService.data.entity.UserEntity;
import com.example.fintrack.UserService.port.UserPort;

public class UserApiImpl implements UserPort {

    private final UserDao userDao;

    public UserApiImpl(Context context) {

        FintrackDatabase db =
                FintrackDatabase.getInstance(context);

        userDao = db.userDao();
    }

    @Override
    public UserEntity getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
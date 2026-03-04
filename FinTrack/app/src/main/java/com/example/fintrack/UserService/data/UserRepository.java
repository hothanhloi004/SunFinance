package com.example.fintrack.UserService.data;

import com.example.fintrack.UserService.entity.User;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    private final List<User> users;
    private User currentUser;

    // ===== LOGIN CONTROL =====
    private int loginFailCount = 0;
    private static final int MAX_LOGIN_FAIL = 3;

    private UserRepository() {
        // LOAD MOCK USERS
        users = FakeDataProvider.getUsers();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    // ===== LOGIN =====
    public boolean login(String username, String password) {

        for (User user : users) {

            if (user.getFullName().equalsIgnoreCase(username)) {

                if (!user.getPassword().equals(password)) {
                    loginFailCount++;
                    return false;
                }

                currentUser = user;
                loginFailCount = 0;
                return true;
            }
        }

        return false;
    }


    // ===== CHECK LOCK =====
    public boolean isLocked() {
        return loginFailCount >= MAX_LOGIN_FAIL;
    }

    // ===== RESET PASSWORD =====
    public String resetPassword(String email) {

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {

                String newPassword = "Abc12345";
                user.setPassword(newPassword); // QUAN TRỌNG

                loginFailCount = 0;
                return newPassword;
            }
        }
        return null;
    }

    // ===== REGISTER =====
    public boolean register(String fullName, String email, String password) {

        // 1. check email tồn tại
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }

        // 2. tạo user mới
        User newUser = new User(
                String.valueOf(System.currentTimeMillis()),
                email,
                fullName,
                "ACTIVE"
        );

        newUser.setPassword(password);

        users.add(newUser);

        currentUser = newUser;
        loginFailCount = 0;

        return true;
    }



    public int getLoginFailCount() {
        return loginFailCount;
    }

    // ===== CURRENT USER =====
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // ===== LOGOUT =====
    public void logout() {
        currentUser = null;
        loginFailCount = 0;
    }


    public void restoreSession(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                currentUser = user;
                loginFailCount = 0;
                break;
            }
        }
    }
}

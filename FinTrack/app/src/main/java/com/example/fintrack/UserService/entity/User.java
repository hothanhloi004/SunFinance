package com.example.fintrack.UserService.entity;

public class User {

    private String userId;
    private String email;
    private String fullName;
    private String status;      // ACTIVE, PREMIUM...
    private String createdAt;   // demo để String cho đơn giản

    private String password; // demo test reset mk

    public User(String userId, String email, String fullName, String status) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
        this.createdAt = "";

        this.password = "123456"; // mật khẩu mặc định
    }

    // Getter
    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStatus() {
        return status;
    }




    // ===== PASSWORD =====
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}

package com.example.fintrack.NotificationService.data;

import android.util.Log;

import com.example.fintrack.NotificationService.util.EmailTemplate;
import com.example.fintrack.NotificationService.util.GmailSender;
import com.example.fintrack.NotificationService.util.OtpManager;
import com.example.fintrack.NotificationService.util.TokenManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private void sendEmail(String to, String subject, String body) {
        executor.execute(() -> {
            try {
                GmailSender.sendHtml(to, subject, body);
                Log.d("NotificationRepo", "Email sent to: " + to);
            } catch (Exception e) {
                Log.e("NotificationRepo", "Email sending failed: " + e.getMessage());
            }
        });
    }
    // ================= OTP & ĐĂNG KÝ =================
    public void sendOtp(String email) {
        String otp = OtpManager.getInstance().generateOtp();
        String html = EmailTemplate.buildOtpEmail(otp);
        sendEmail(email, "FinTrack Account Verification OTP", html);
    }

    public boolean verifyOtp(String inputOtp, String email, String username, String password) {
        boolean valid = OtpManager.getInstance().verifyOtp(inputOtp);
        if (valid) {
            String html = EmailTemplate.buildAccountInfo(username, password);
            sendEmail(email, "FinTrack Registration Successful", html);
        }
        return valid;
    }
    // ================= QUÊN MẬT KHẨU =================
    public void requestResetPassword(String email) {
        String token = TokenManager.getInstance().generateResetToken(email);
        String resetLink = "https://fintrackapp/resetpassword?token=" + token + "&email=" + email;
        String htmlContent = EmailTemplate.buildResetLink(resetLink);
        sendEmail(email, "FinTrack Password Reset Request", htmlContent);
    }
    // ================= THÔNG BÁO SỐ DƯ =================
    public void notifyBalance(String email, String content) {
        String html = EmailTemplate.buildBalanceNotice(content);
        sendEmail(email, "Balance Change Notification", html);
    }
}
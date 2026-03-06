package com.example.fintrack.AccountService.util;

public class CurrencyMapper {
    public static String formatVND(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}

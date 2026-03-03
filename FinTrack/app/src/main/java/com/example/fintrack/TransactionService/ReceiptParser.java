package com.example.fintrack.TransactionService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptParser {

    public static String extractTotal(String text) {

        text = text.replace(",", ""); // bỏ dấu phẩy nếu có

        Pattern pattern = Pattern.compile("\\$?\\s*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(text);

        String lastAmount = "";
        while (matcher.find()) {
            lastAmount = matcher.group().trim();
        }

        return lastAmount;
    }

    public static String extractDate(String text) {

        Pattern pattern = Pattern.compile("\\d{2}[-/]\\d{2}[-/]\\d{4}");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group().trim();
        }

        return "";
    }

    public static String extractStoreName(String text) {

        String[] lines = text.split("\\r?\\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }

        return "";
    }
}
package com.example.fintrack.TransactionService.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptParser {

    public static String extractTotal(String text) {

        text = text.replace(",", "");

        Pattern pattern = Pattern.compile("\\$?\\s*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(text);

        String lastAmount = "";
        while (matcher.find()) {
            lastAmount = matcher.group().trim();
        }

        return lastAmount.replace("$", ""); // bỏ $
    }

    public static String extractDate(String text) {

        Pattern pattern = Pattern.compile("\\d{1,2}[-/]\\d{1,2}[-/]\\d{4}");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {

            String rawDate = matcher.group().trim();

            try {

                String[] parts = rawDate.split("[-/]");

                String day = parts[0];
                String month = parts[1];
                String year = parts[2];

                return day + "-" + month + "-" + year; // dd-MM-yyyy

            } catch (Exception e) {
                e.printStackTrace();
            }
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
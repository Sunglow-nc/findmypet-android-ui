package com.example.findmypet_android_ui.ui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static boolean isUKNumber(String number){
        Pattern validPhoneNumber = Pattern.compile("^(\\+44\\s?|0)(?:\\d{2}\\s?\\d{4}\\s?\\d{4}|\\d{3}\\s?\\d{3}\\s?\\d{4}|\\d{4}\\s?\\d{3}\\s?\\d{3})$");
        Matcher matcher = validPhoneNumber.matcher(number);
        return matcher.find();
    }

    public static boolean isValidEmail(String email){
        Pattern validPhoneNumber = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
        Matcher matcher = validPhoneNumber.matcher(email);
        return matcher.find();
    }
}

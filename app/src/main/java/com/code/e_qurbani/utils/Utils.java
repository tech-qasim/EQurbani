package com.code.e_qurbani.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static boolean validateUserName(String userName) {
        // Regex to check valid username.
        final String regex = "^[a-zA-Z]+ [a-zA-Z]+$";

        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        return !matcher.matches();
    }

    public static boolean validateName(String generalString) {
        final String regex = "^[A-Za-z]{5,29}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generalString);
        return !matcher.matches();
    }

    public static boolean validateDate(String generalString) {
        final String regex = "\\b(\\d{1,2}-[A-Za-z]{3}-\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generalString);
        return !matcher.matches();
    }

    public static boolean validateIsNumber(String generalString) {
        final String regex = "(\\d{1})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generalString);
        return matcher.matches();
    }

    public static boolean validateWeight(String generalString) {
        final String regex = "^[0-9]{1,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generalString);
        return !matcher.matches();
    }

    public static boolean validateAmount(String generalString) {
        final String regex = "^[0-9]{3,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generalString);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        // Regex to check valid username.
        final String regex = "^(.+)@(.+)$";
        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    public static boolean validatePhoneNumber(EditText etUserName, String strEtUserName) {
        // Regex to check valid username.
        final String regex = "^((\\+92)?(0092)?(92)?(0)?)(3)([0-9]{9})$";
        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strEtUserName);
        if (!matcher.matches()) {
            etUserName.setError("INVALID PHONE NUMBER");
        }
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String strEtUserName) {
        // Regex to check valid username.
        final String regex = "^((\\+92)?(0092)?(92)?(0)?)(3)([0-9]{9})$";
        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strEtUserName);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        // Regex to check valid username.
        final String regex = "^(?=.*[0-9])"
                                     + "(?=.*[a-z])(?=.*[A-Z])"
                                     + "(?=.*[@#$%^&+=])"
                                     + "(?=\\S+$).{8,20}$";
        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return !matcher.matches();
    }

}

 
package com.example.demo.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {


        private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        public static boolean isValidPassword(String password) {
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }
}

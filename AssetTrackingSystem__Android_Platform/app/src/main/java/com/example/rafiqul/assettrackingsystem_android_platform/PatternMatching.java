package com.example.rafiqul.assettrackingsystem_android_platform;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by rafiqul on 4/17/16.
 */
public class PatternMatching {
    public static boolean emailValidation(String emailAddress){
        String EmailPattern;
        Pattern pattern;
        EmailPattern = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        pattern = Pattern.compile(EmailPattern);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
}

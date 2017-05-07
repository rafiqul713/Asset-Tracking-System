package com.example.rafiqul.assettrackingsystem_android_platform;

/**
 * Created by rafiqul on 4/19/16.
 */
public class New_User {
    String USER_NAME,USER_ID,USER_EMAIL,USER_PASS,USER_TYPE;
    public New_User(String USER_NAME, String USER_ID, String USER_EMAIL, String USER_PASS, String USER_TYPE) {
        this.USER_NAME = USER_NAME;
        this.USER_ID = USER_ID;
        this.USER_EMAIL = USER_EMAIL;
        this.USER_PASS = USER_PASS;
        this.USER_TYPE = USER_TYPE;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_EMAIL() {
        return USER_EMAIL;
    }

    public void setUSER_EMAIL(String USER_EMAIL) {
        this.USER_EMAIL = USER_EMAIL;
    }

    public String getUSER_PASS() {
        return USER_PASS;
    }

    public void setUSER_PASS(String USER_PASS) {
        this.USER_PASS = USER_PASS;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }
}

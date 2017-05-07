package com.example.rafiqul.assettrackingsystem_android_platform;

/**
 * Created by rafiqul on 4/16/16.
 */
public class Person {
    String FULLNAME,USERNAME,EMAIL,PASSWORD;

    public Person(String FULLNAME, String USERNAME, String EMAIL, String PASSWORD) {
        this.FULLNAME = FULLNAME;
        this.USERNAME = USERNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}

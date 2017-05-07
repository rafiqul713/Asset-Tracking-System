package com.example.rafiqul.assettrackingsystem_android_platform;

/**
 * Created by rafiqul on 4/16/16.
 */
public class Company_Info {
    String CompanyName,CompanyLocation,CompanyPhone,CompanyEmail;
    public Company_Info(String companyName, String companyLocation, String companyPhone, String companyEmail) {
        CompanyName = companyName;
        CompanyLocation = companyLocation;
        CompanyPhone = companyPhone;
        CompanyEmail = companyEmail;
    }
    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName)
    {
        CompanyName = companyName;
    }

    public String getCompanyLocation()
    {
        return CompanyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        CompanyLocation = companyLocation;
    }

    public String getCompanyPhone() {
        return CompanyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        CompanyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return CompanyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        CompanyEmail = companyEmail;
    }


}

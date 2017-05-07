package com.example.rafiqul.assettrackingsystem_android_platform;

/**
 * Created by rafiqul on 4/16/16.
 */
public class Asset_Info {
    String Asset_TAG, Asset_Type,Asset_AssignTo,Asset_Location;
    public Asset_Info(String asset_TAG, String asset_Type, String asset_AssignTo, String asset_Location) {
        Asset_TAG = asset_TAG;
        Asset_Type = asset_Type;
        Asset_AssignTo = asset_AssignTo;
        Asset_Location = asset_Location;
    }


    public String getAsset_TAG() {
        return Asset_TAG;
    }

    public void setAsset_TAG(String asset_TAG) {
        Asset_TAG = asset_TAG;
    }

    public String getAsset_Type() {
        return Asset_Type;
    }

    public void setAsset_Type(String asset_Type) {
        Asset_Type = asset_Type;
    }

    public String getAsset_AssignTo() {
        return Asset_AssignTo;
    }

    public void setAsset_AssignTo(String asset_AssignTo) {
        Asset_AssignTo = asset_AssignTo;
    }

    public String getAsset_Location() {
        return Asset_Location;
    }

    public void setAsset_Location(String asset_Location) {
        Asset_Location = asset_Location;
    }
}

package com.example.cities.model.data;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * DTO representing aboutInfo object
 */

public class AboutInfo {

    @SerializedName("companyName")
    private final String companyName;

    @SerializedName("companyAddress")
    private final String companyAddress;

    @SerializedName("postalCode")
    private final String companyPostal;

    @SerializedName("city")
    private final String companyCity;

    @SerializedName("details")
    private final String aboutInfo;

    public AboutInfo(String companyName, String companyAddress, String companyPostal, String companyCity, String aboutInfo) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPostal = companyPostal;
        this.companyCity = companyCity;
        this.aboutInfo = aboutInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyPostal() {
        return companyPostal;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public String getAboutInfo() {
        return aboutInfo;
    }

    @NotNull
    @Override
    public String toString() {
        return "AboutInfo{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyPostal='" + companyPostal + '\'' +
                ", companyCity='" + companyCity + '\'' +
                ", aboutInfo='" + aboutInfo + '\'' +
                '}';
    }
}

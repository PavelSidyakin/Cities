package com.example.cities.model;

import com.example.cities.model.data.AboutInfo;

import org.jetbrains.annotations.NotNull;

public class AboutInfoResultData {

    private final AboutInfo aboutInfo;

    public AboutInfoResultData(AboutInfo aboutInfo) {
        this.aboutInfo = aboutInfo;
    }

    public AboutInfo getAboutInfo() {
        return aboutInfo;
    }

    @NotNull
    @Override
    public String toString() {
        return "AboutInfoResultData{" +
                "aboutInfo=" + aboutInfo +
                '}';
    }
}

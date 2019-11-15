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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AboutInfoResultData that = (AboutInfoResultData) o;

        return aboutInfo != null ? aboutInfo.equals(that.aboutInfo) : that.aboutInfo == null;
    }

    @Override
    public int hashCode() {
        return aboutInfo != null ? aboutInfo.hashCode() : 0;
    }

    @NotNull
    @Override
    public String toString() {
        return "AboutInfoResultData{" +
                "aboutInfo=" + aboutInfo +
                '}';
    }
}

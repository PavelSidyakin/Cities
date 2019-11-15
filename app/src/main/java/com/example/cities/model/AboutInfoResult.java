package com.example.cities.model;

import org.jetbrains.annotations.NotNull;

public class AboutInfoResult {

    private final AboutInfoResultCode resultCode;
    private final AboutInfoResultData data;

    public AboutInfoResult(AboutInfoResultCode resultCode, AboutInfoResultData data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public AboutInfoResultCode getResultCode() {
        return resultCode;
    }

    public AboutInfoResultData getData() {
        return data;
    }

    @NotNull
    @Override
    public String toString() {
        return "AboutInfoResult{" +
                "resultCode=" + resultCode +
                ", data=" + data +
                '}';
    }
}

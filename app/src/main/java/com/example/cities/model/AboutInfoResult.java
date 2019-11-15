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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AboutInfoResult that = (AboutInfoResult) o;

        if (resultCode != that.resultCode) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = resultCode != null ? resultCode.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
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

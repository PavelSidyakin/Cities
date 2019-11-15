package com.example.cities.model;

import org.jetbrains.annotations.NotNull;

public class CitiesSearchResult {

    private final CitiesSearchResultCode resultCode;
    private final CitiesSearchResultData resultData;

    public CitiesSearchResult(CitiesSearchResultCode resultCode, CitiesSearchResultData resultData) {
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    public CitiesSearchResultCode getResultCode() {
        return resultCode;
    }

    public CitiesSearchResultData getResultData() {
        return resultData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesSearchResult that = (CitiesSearchResult) o;

        if (resultCode != that.resultCode) return false;
        return resultData != null ? resultData.equals(that.resultData) : that.resultData == null;
    }

    @Override
    public int hashCode() {
        int result = resultCode != null ? resultCode.hashCode() : 0;
        result = 31 * result + (resultData != null ? resultData.hashCode() : 0);
        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return "CitiesSearchResult{" +
                "resultCode=" + resultCode +
                ", resultData=" + resultData +
                '}';
    }
}

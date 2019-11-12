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

    @NotNull
    @Override
    public String toString() {
        return "CitiesSearchResult{" +
                "resultCode=" + resultCode +
                ", resultData=" + resultData +
                '}';
    }
}

package com.example.cities.data;

import android.content.Context;

import com.example.cities.TheApplication;
import com.example.cities.domain.ApplicationProvider;

import javax.inject.Inject;

public class ApplicationProviderImpl implements ApplicationProvider {

    private TheApplication theApplication;

    @Inject
    ApplicationProviderImpl() {
    }

    @Override
    public void init(TheApplication theApplication) {
        this.theApplication = theApplication;
    }

    @Override
    public Context getApplicationContext() {
        if (theApplication == null) {
            throw new RuntimeException("theApplication is not initialized. init() must be called");
        }

        return theApplication.getApplicationContext();
    }

}

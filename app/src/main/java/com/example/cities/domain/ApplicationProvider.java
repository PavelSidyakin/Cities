package com.example.cities.domain;

import android.content.Context;

import com.example.cities.TheApplication;

public interface ApplicationProvider {

    /**
     * Initializes ApplicationProvider. Should be called at {@link TheApplication#onCreate()}
     *
     * @param theApplication Reference to TheApplication object
     */
    void init(TheApplication theApplication);

    /**
     * Returns application context.
     *
     * @return Application Context
     */
    Context getApplicationContext();

}

package com.example.cities.domain;

import android.content.Context;

import com.example.cities.TheApplication;

public interface ApplicationProvider {

    void init(TheApplication theApplication);

    Context getApplicationContext();

}

package com.example.cities;

import com.example.cities.di.app.AppComponent;
import com.example.cities.di.app.AppStubModuleForTests;
import com.example.cities.di.app.DaggerAppComponent;
import com.example.cities.presentation.main_screen.view.MainActivityTest;

public class TheApplicationForTest extends TheApplication {

    @Override
    protected AppComponent createAppComponent() {
        MainActivityTest.setUpBeforeAppCreation();

        return DaggerAppComponent.builder()
                .appStubModule(new AppStubModuleForTests())
                .build();
    }
}
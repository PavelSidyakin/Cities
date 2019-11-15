package com.example.cities.domain.about_app;

import com.example.cities.model.AboutInfoResult;

import io.reactivex.Single;

public interface AboutAppInfoInteractor {

    Single<AboutInfoResult> requestAboutInfo();

}

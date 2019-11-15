package com.example.cities.domain.about_app;

import com.example.cities.model.data.AboutInfo;

import io.reactivex.Single;

public interface AboutAppInfoRepository {

    Single<AboutInfo> requestAboutInfo();

}

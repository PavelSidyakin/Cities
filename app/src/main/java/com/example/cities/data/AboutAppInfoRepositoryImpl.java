package com.example.cities.data;

import android.content.res.AssetManager;

import com.example.cities.domain.about_app.AboutAppInfoRepository;
import com.example.cities.model.data.AboutInfo;
import com.example.cities.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.inject.Inject;

import io.reactivex.Single;

public class AboutAppInfoRepositoryImpl implements AboutAppInfoRepository {

    private final ApplicationProvider applicationProvider;
    private final SchedulerProvider schedulerProvider;

    private static final String ASSETS_ABOUT_INFO_FILE_NAME = "aboutInfo.json";

    @Inject
    AboutAppInfoRepositoryImpl(ApplicationProvider applicationProvider, SchedulerProvider schedulerProvider) {
        this.applicationProvider = applicationProvider;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<AboutInfo> requestAboutInfo() {
        return Single.fromCallable(() -> getAboutInfoFromAssets())
            .flatMap(aboutInfoJsonStr -> aboutInfoJsonStr.isEmpty() ?
                    Single.error(new RuntimeException("Empty about info"))
                    :  Single.fromCallable(() -> parseAboutInfo(aboutInfoJsonStr)))
            .subscribeOn(schedulerProvider.io());
    }

    private AboutInfo parseAboutInfo(String aboutInfoJson) {
        return new Gson().fromJson(new JsonReader(new StringReader(aboutInfoJson)),
                new TypeToken<AboutInfo>() {}.getType());
    }

    private String getAboutInfoFromAssets() {
        try {
            AssetManager manager = applicationProvider.getApplicationContext().getAssets();
            InputStream file = manager.open(ASSETS_ABOUT_INFO_FILE_NAME);
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to get about info from assets", ex);
        }
    }
}

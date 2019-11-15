package com.example.cities.data;

import android.content.res.AssetManager;

import com.example.cities.domain.about_app.AboutAppInfoRepository;
import com.example.cities.model.data.AboutInfo;
import com.example.cities.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Single;

public class AboutAppInfoRepositoryImpl implements AboutAppInfoRepository {

    private final ApplicationProvider applicationProvider;
    private final SchedulerProvider schedulerProvider;

    private static final String FILE_NAME = "aboutInfo.json";

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
        try {
            JSONObject jsonObject = new JSONObject(aboutInfoJson);
            AboutInfo aboutInfo = null;
            aboutInfo = new AboutInfo();
            aboutInfo.setCompanyName(jsonObject.getString("companyName"));
            aboutInfo.setCompanyAddress(jsonObject.getString("companyAddress"));
            aboutInfo.setCompanyCity(jsonObject.getString("city"));
            aboutInfo.setCompanyPostal(jsonObject.getString("postalCode"));
            aboutInfo.setAboutInfo(jsonObject.getString("details"));
            return aboutInfo;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to parse about info", e);
        }
    }

    private String getAboutInfoFromAssets() {
        try{
            AssetManager manager = applicationProvider.getApplicationContext().getAssets();
            InputStream file = manager.open(FILE_NAME);
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        }catch (IOException ex) {
            throw new RuntimeException("Failed to get about info from assets", ex);
        }
    }
}

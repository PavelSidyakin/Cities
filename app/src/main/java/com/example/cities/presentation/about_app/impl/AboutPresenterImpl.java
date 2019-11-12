package com.example.cities.presentation.about_app.impl;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.cities.presentation.about_app.About;
import com.example.cities.presentation.about_app.model.AboutInfo;

import java.lang.ref.WeakReference;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

public class AboutPresenterImpl implements About.Presenter {

    private final WeakReference<About.View> aboutView;
    private final AboutDataImpl aboutData;

    public AboutPresenterImpl(About.View view, @NonNull Context context) {
        this.aboutView = new WeakReference<>(view);
        this.aboutData = new AboutDataImpl(this, context);
    }

    @Override
    public void requestAboutInfo() {
        About.View aboutViewImpl = aboutView.get();

        aboutViewImpl.showProgress();

        new Handler().postDelayed(() -> aboutData.requestAboutInfo(), 1000);
    }

    @Override
    public void onSuccess(AboutInfo aboutInfo) {
        About.View aboutViewImpl = aboutView.get();

        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress();
            aboutViewImpl.setCompanyName(aboutInfo.getCompanyName());
            aboutViewImpl.setCompanyAddress(aboutInfo.getCompanyAddress());
            aboutViewImpl.setCompanyPostalCode(aboutInfo.getCompanyPostal());
            aboutViewImpl.setCompanyCity(aboutInfo.getCompanyCity());
            aboutViewImpl.setAboutInfo(aboutInfo.getAboutInfo());
        }

    }

    @Override
    public void onFail() {
        About.View aboutViewImpl = aboutView.get();
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress();
            aboutViewImpl.showError();
        }
    }
}

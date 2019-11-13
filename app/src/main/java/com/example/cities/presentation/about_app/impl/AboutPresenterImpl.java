package com.example.cities.presentation.about_app.impl;

import android.util.Log;

import com.example.cities.domain.ApplicationProvider;
import com.example.cities.presentation.about_app.About;
import com.example.cities.presentation.about_app.model.AboutInfo;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

public class AboutPresenterImpl implements About.Presenter {

    private final About.View aboutViewRef;
    private final About.Data aboutData;
    private final SchedulerProvider schedulerProvider;

    private Disposable requestAboutInfoDisposable;

    private static final String TAG = "AboutPresenter";

    @Inject
    public AboutPresenterImpl(About.View view, ApplicationProvider applicationProvider, SchedulerProvider schedulerProvider) {
        this.aboutViewRef = view;
        this.aboutData = new AboutDataImpl(applicationProvider, schedulerProvider);
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void requestAboutInfo() {
        About.View aboutView = aboutViewRef;
        if (aboutView != null) {
            aboutView.showProgress();
        }

        requestAboutInfoDisposable = Completable.timer(1000, TimeUnit.MILLISECONDS)
            .andThen(Single.defer(() -> aboutData.requestAboutInfo() ))
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .doFinally(() -> requestAboutInfoDisposable = null)
            .subscribe(aboutInfo -> onSuccess(aboutInfo),
                throwable -> {
                    Log.w(TAG, "AboutPresenterImpl.requestAboutInfo() Failed", throwable);
                    onFail();
            });
    }

    @Override
    public void onSuccess(AboutInfo aboutInfo) {
        About.View aboutViewImpl = aboutViewRef;

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
        About.View aboutViewImpl = aboutViewRef;
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress();
            aboutViewImpl.showError();
        }
    }

    @Override
    public void onDestroyView() {
        if (requestAboutInfoDisposable != null && !requestAboutInfoDisposable.isDisposed()) {
            requestAboutInfoDisposable.dispose();
        }
    }
}

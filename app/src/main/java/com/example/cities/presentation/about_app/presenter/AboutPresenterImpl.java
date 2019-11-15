package com.example.cities.presentation.about_app.presenter;

import com.example.cities.domain.about_app.AboutAppInfoInteractor;
import com.example.cities.model.AboutInfoResult;
import com.example.cities.presentation.about_app.About;
import com.example.cities.model.data.AboutInfo;
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

    private final About.View aboutView;
    private final AboutAppInfoInteractor aboutAppInfoInteractor;
    private final SchedulerProvider schedulerProvider;

    private Disposable requestAboutInfoDisposable;

    private static final String TAG = "AboutPresenter";

    @Inject
    AboutPresenterImpl(About.View view, SchedulerProvider schedulerProvider, AboutAppInfoInteractor aboutAppInfoInteractor) {
        this.aboutView = view;
        this.aboutAppInfoInteractor = aboutAppInfoInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onViewReady() {
        aboutView.showProgress();

        requestAboutInfoDisposable = Completable.timer(1000, TimeUnit.MILLISECONDS)
            .andThen(Single.defer(() -> aboutAppInfoInteractor.requestAboutInfo() ))
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .doFinally(() -> requestAboutInfoDisposable = null)
            .subscribe(aboutInfoResult -> processAboutInfoResult(aboutInfoResult));
    }

    private void processAboutInfoResult(AboutInfoResult aboutInfoResult) {
        switch (aboutInfoResult.getResultCode()) {
            case OK:
                onSuccess(aboutInfoResult.getData().getAboutInfo());
                break;
            case ERROR:
            default:
                onFail();
                break;
        }
    }

    @Override
    public void onSuccess(AboutInfo aboutInfo) {
        aboutView.hideProgress();
        aboutView.setCompanyName(aboutInfo.getCompanyName());
        aboutView.setCompanyAddress(aboutInfo.getCompanyAddress());
        aboutView.setCompanyPostalCode(aboutInfo.getCompanyPostal());
        aboutView.setCompanyCity(aboutInfo.getCompanyCity());
        aboutView.setAboutInfo(aboutInfo.getAboutInfo());
    }

    @Override
    public void onFail() {
        aboutView.hideProgress();
        aboutView.showError();
    }

    @Override
    public void onDestroyView() {
        if (requestAboutInfoDisposable != null && !requestAboutInfoDisposable.isDisposed()) {
            requestAboutInfoDisposable.dispose();
        }
    }
}

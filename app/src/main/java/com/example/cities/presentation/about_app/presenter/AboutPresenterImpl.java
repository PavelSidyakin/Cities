package com.example.cities.presentation.about_app.presenter;

import androidx.annotation.VisibleForTesting;

import com.example.cities.domain.about_app.AboutAppInfoInteractor;
import com.example.cities.model.AboutInfoResult;
import com.example.cities.presentation.about_app.About;
import com.example.cities.model.data.AboutInfo;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

public class AboutPresenterImpl implements About.Presenter {

    private final About.View aboutView;
    private final AboutAppInfoInteractor aboutAppInfoInteractor;
    private final SchedulerProvider schedulerProvider;

    @VisibleForTesting
    Disposable requestAboutInfoDisposable;

    private static final String TAG = "AboutPresenter";

    private static final long DEFAULT_REQUEST_DELAY_MS = 1000;
    @VisibleForTesting
    long requestDelayMs = DEFAULT_REQUEST_DELAY_MS;

    @VisibleForTesting
    CompletableSubject requestCompletedCompletable = CompletableSubject.create();

    @Inject
    AboutPresenterImpl(About.View view, SchedulerProvider schedulerProvider, AboutAppInfoInteractor aboutAppInfoInteractor) {
        this.aboutView = view;
        this.aboutAppInfoInteractor = aboutAppInfoInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onViewReady() {
        aboutView.showProgress();

        requestAboutInfoDisposable = Completable.timer(requestDelayMs, TimeUnit.MILLISECONDS)
            .andThen(Single.defer(() -> aboutAppInfoInteractor.requestAboutInfo() ))
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .doFinally(() -> requestCompletedCompletable.onComplete())
            .doOnSubscribe(disposable -> XLog.i(TAG, "AboutPresenterImpl.onViewReady(): Subscribe. "))
            .doOnSuccess(result -> XLog.i(TAG, "AboutPresenterImpl.onViewReady(): Success. result=" + result))
            .doOnError(throwable -> XLog.w(TAG, "AboutPresenterImpl.onViewReady(): Error", throwable))
            .subscribe(aboutInfoResult -> processAboutInfoResult(aboutInfoResult),
                    throwable -> onFail());
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

    private void onSuccess(AboutInfo aboutInfo) {
        aboutView.hideProgress();
        aboutView.setCompanyName(aboutInfo.getCompanyName());
        aboutView.setCompanyAddress(aboutInfo.getCompanyAddress());
        aboutView.setCompanyPostalCode(aboutInfo.getCompanyPostal());
        aboutView.setCompanyCity(aboutInfo.getCompanyCity());
        aboutView.setAboutInfo(aboutInfo.getAboutInfo());
    }

    private void onFail() {
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

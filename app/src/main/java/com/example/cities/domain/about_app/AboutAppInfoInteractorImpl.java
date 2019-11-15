package com.example.cities.domain.about_app;

import com.example.cities.model.AboutInfoResult;
import com.example.cities.model.AboutInfoResultCode;
import com.example.cities.model.AboutInfoResultData;
import com.example.cities.utils.XLog;

import javax.inject.Inject;

import io.reactivex.Single;

public class AboutAppInfoInteractorImpl implements AboutAppInfoInteractor {

    private final AboutAppInfoRepository aboutAppInfoRepository;

    private static final String TAG = "AboutAppInfoInteractor";

    @Inject
    AboutAppInfoInteractorImpl(AboutAppInfoRepository aboutAppInfoRepository) {
        this.aboutAppInfoRepository = aboutAppInfoRepository;
    }

    @Override
    public Single<AboutInfoResult> requestAboutInfo() {
        return aboutAppInfoRepository.requestAboutInfo()
            .map(aboutInfo -> new AboutInfoResult(AboutInfoResultCode.OK, new AboutInfoResultData(aboutInfo)))
            .doOnSubscribe(disposable -> XLog.i(TAG, "AboutAppInfoInteractorImpl.requestAboutInfo(): Subscribe. "))
            .doOnSuccess(result -> XLog.i(TAG, "AboutAppInfoInteractorImpl.requestAboutInfo(): Success. result=" + result))
            .doOnError(throwable -> XLog.w(TAG, "AboutAppInfoInteractorImpl.requestAboutInfo(): Error", throwable))
            .onErrorResumeNext(throwable -> Single.just(new AboutInfoResult(AboutInfoResultCode.ERROR, null)));
    }
}

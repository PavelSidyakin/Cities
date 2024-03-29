package com.example.cities.domain.initialization.impl;

import com.example.cities.domain.cities_search.CitiesSearchPreLoadInteractor;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;

public class InitializationHelperImpl implements InitializationHelper {

    private final CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor;
    private final SchedulerProvider schedulerProvider;
    private final InitializationProgressHolder initializationProgressHolder;

    private static final String TAG = "Initialization";

    @Inject
    InitializationHelperImpl(CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor,
                             SchedulerProvider schedulerProvider,
                             InitializationProgressHolder initializationProgressHolder) {

        this.citiesSearchPreLoadInteractor = citiesSearchPreLoadInteractor;
        this.schedulerProvider = schedulerProvider;
        this.initializationProgressHolder = initializationProgressHolder;
    }

    @Override
    public void launchInitialization() {
        Completable.defer(() -> citiesSearchPreLoadInteractor.preLoad())
            .doOnComplete(() -> initializationProgressHolder.onInitializationCompleted())
            .doOnError(throwable -> initializationProgressHolder.onInitializationFailed(throwable))
            .doOnSubscribe(disposable -> XLog.i(TAG, "InitializationHelperImpl.launchInitialization(): Subscribe. "))
            .doOnComplete(() -> XLog.i(TAG, "InitializationHelperImpl.launchInitialization(): Completed."))
            .doOnError(throwable -> XLog.w(TAG, "InitializationHelperImpl.launchInitialization(): Error", throwable))
            .subscribeOn(schedulerProvider.io())
            .subscribe();
    }
}

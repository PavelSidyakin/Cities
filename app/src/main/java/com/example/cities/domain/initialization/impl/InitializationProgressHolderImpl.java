package com.example.cities.domain.initialization.impl;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

public class InitializationProgressHolderImpl implements InitializationProgressHolder {

    private final CompletableSubject initializationCompletableSubject = CompletableSubject.create();
    private volatile boolean isInitialized = false;

    @Inject
    InitializationProgressHolderImpl() {
    }

    @Override
    public void onInitializationCompleted() {
        isInitialized = true;
        initializationCompletableSubject.onComplete();
    }

    @Override
    public void onInitializationFailed(Throwable reason) {
        initializationCompletableSubject.onError(reason);
    }

    @Override
    public Completable observeInitializationCompleteness() {
        return initializationCompletableSubject;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }
}

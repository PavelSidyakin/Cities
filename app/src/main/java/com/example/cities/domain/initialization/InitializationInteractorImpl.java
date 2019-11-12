package com.example.cities.domain.initialization;

import com.example.cities.domain.initialization.impl.InitializationProgressHolder;

import javax.inject.Inject;

import io.reactivex.Completable;

public class InitializationInteractorImpl implements InitializationInteractor {

    private final InitializationProgressHolder initializationProgressHolder;

    @Inject
    InitializationInteractorImpl(InitializationProgressHolder initializationProgressHolder) {
        this.initializationProgressHolder = initializationProgressHolder;
    }

    @Override
    public Completable observeInitializationCompleteness() {
        if (initializationProgressHolder.isInitialized()) {
            return Completable.complete();
        } else {
            return initializationProgressHolder.observeInitializationCompleteness();
        }
    }
}

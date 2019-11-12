package com.example.cities.domain.initialization.impl;

import io.reactivex.Completable;

public interface InitializationProgressHolder {

    /**
     * Should be called on initialization process completed
     */
    void onInitializationCompleted();

    /**
     * Should be called on initialization process failed
     */
    void onInitializationFailed(Throwable reason);

    /**
     * Returns subject for initialization process.
     *
     * @return Completable for initialization process.
     * Completes after initialization process completed.
     * Completes immediately if initialization already completed.
     *
     * scheduler: does not operate by default on a particular Scheduler.
     * error: if the initialization failed.
     */
    Completable observeInitializationCompleteness();

    /**
     * Indicates that initialization process has been completed.
     *
     * @return true if initialization process has been completed, false otherwise
     */
    boolean isInitialized();

}

package com.example.cities.domain.initialization;

import io.reactivex.Completable;

public interface InitializationInteractor {

    /**
     * Returns subject for initialization process.
     *
     * @return Completable for initialization process.
     * Completes after initialization process completed.
     * Completes immediately if initialization already completed.
     *
     * subscribe: does not operate by default on a particular Scheduler.
     * error: if the initialization failed.
     */
    Completable observeInitializationCompleteness();

}

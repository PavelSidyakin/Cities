package com.example.cities.presentation.main_screen.presenter;

import com.example.cities.domain.initialization.InitializationInteractor;
import com.example.cities.presentation.main_screen.MainScreen;
import com.example.cities.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainScreenPresenterImpl implements MainScreen.Presenter {

    private final MainScreen.View view;
    private final InitializationInteractor initializationInteractor;
    private final SchedulerProvider schedulerProvider;

    private Disposable waitInitializationDisposable;

    @Inject
    public MainScreenPresenterImpl(MainScreen.View view,
                                   InitializationInteractor initializationInteractor,
                                   SchedulerProvider schedulerProvider) {
        this.view = view;
        this.initializationInteractor = initializationInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onViewReady() {
        waitInitializationDisposable = initializationInteractor.observeInitializationCompleteness()
                .doOnSubscribe(disposable -> view.showLoadingProgress())
                .doFinally(() -> view.hideLoadingProgress())
                .doOnComplete(() -> view.showCitiesSearchScreen())
                .subscribeOn(schedulerProvider.main())
                .subscribe();
    }

    @Override
    public void onDestroyView() {
        if (waitInitializationDisposable != null && !waitInitializationDisposable.isDisposed()) {
            waitInitializationDisposable.dispose();
            waitInitializationDisposable = null;
        }
    }
}

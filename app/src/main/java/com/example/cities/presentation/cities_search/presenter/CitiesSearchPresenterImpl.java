package com.example.cities.presentation.cities_search.presenter;

import androidx.annotation.VisibleForTesting;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.presentation.cities_search.presenter.recycler.CitiesSearchDataSourceFactory;
import com.example.cities.utils.Lazy;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

public class CitiesSearchPresenterImpl implements CitiesSearch.Presenter {

    private final CitiesSearch.View view;
    private final SchedulerProvider schedulerProvider;
    private final CitiesSearchInteractor citiesSearchInteractor;
    private final CitiesScreenInteractor citiesScreenInteractor;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ObservableEmitter<String> performSearchObservableEmitter = null;
    private Observable<String> performSearchObservable = Observable.create(emitter -> performSearchObservableEmitter = emitter);

    private ObservableEmitter<String> submitSearchObservableEmitter = null;
    private Observable<String> submitSearchObservable = Observable.create(emitter ->  submitSearchObservableEmitter = emitter);

    private ObservableEmitter<Object> retryObservableEmitter = null;
    private Observable<Object> retryObservable = Observable.create(emitter ->  retryObservableEmitter = emitter);

    @VisibleForTesting
    int initialPageSizeFactor = DEFAULT_INITIAL_PAGE_SIZE_FACTOR;
    @VisibleForTesting
    int pageSize = DEFAULT_PAGE_SIZE;

    @VisibleForTesting
    long performSearchTimeoutMillis = DEFAULT_PERFORM_SEARCH_TIMEOUT_MILLIS;

    private static final String TAG = "CitiesSearchPresenter";
    private static final long DEFAULT_PERFORM_SEARCH_TIMEOUT_MILLIS = 500L;

    @VisibleForTesting
    static final int DEFAULT_PAGE_SIZE = 50;
    @VisibleForTesting
    static final int DEFAULT_INITIAL_PAGE_SIZE_FACTOR = 3;

    @VisibleForTesting
    CompletableSubject requestCompletedCompletable = CompletableSubject.create();

    private Lazy<PagedList.Config> pageListConfig = new Lazy<>(() -> new PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(initialPageSizeFactor * pageSize)
            .setEnablePlaceholders(false)
            .build());

    @Inject
    CitiesSearchPresenterImpl(CitiesSearch.View view,
                              SchedulerProvider schedulerProvider,
                              CitiesSearchInteractor citiesSearchInteractor,
                              CitiesScreenInteractor citiesScreenInteractor) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.citiesSearchInteractor = citiesSearchInteractor;
        this.citiesScreenInteractor = citiesScreenInteractor;
    }


    @Override
    public void onViewReady() {
        Disposable searchTextDisposable = Observable.merge(
                    Observable.fromCallable(() -> citiesScreenInteractor.getCurrentSearchText()),
                    submitSearchObservable,
                    performSearchObservable.debounce(performSearchTimeoutMillis, TimeUnit.MILLISECONDS))
                .flatMap(searchString ->preProcessInputString(searchString))
                .doOnNext(searchString -> XLog.i(TAG, "Filtered search string: " + searchString))
                .distinctUntilChanged()
                .doOnNext(searchString -> citiesScreenInteractor.setCurrentSearchText(searchString))
                .switchMap(searchString -> buildRequestObservable(searchString) )
                .subscribeOn(schedulerProvider.main())
                .subscribe(cityDataPagedList -> {
                    view.updateCityList(cityDataPagedList);
                    requestCompletedCompletable.onComplete();
                }, throwable -> {
                    XLog.w(TAG, "Error in search text chain", throwable);
                    requestCompletedCompletable.onError(throwable);
                });

        compositeDisposable.add(searchTextDisposable);

        String currentSearchText = citiesScreenInteractor.getCurrentSearchText();
        if (currentSearchText != null && !currentSearchText.isEmpty()) {
            view.setSearchText(currentSearchText);
        }

        view.hideError();
        view.hideProgress();
    }

    @Override
    public void onSearchTextChanged(String searchText) {
        if (performSearchObservableEmitter != null) {
            performSearchObservableEmitter.onNext(searchText);
        }
    }

    @Override
    public void onSearchTextSubmitted(String searchText) {
        if (submitSearchObservableEmitter != null) {
            submitSearchObservableEmitter.onNext(searchText);
        }
    }

    @Override
    public void onCityClicked(CityData cityData) {
        citiesScreenInteractor.setCurrentSelectedCity(cityData);
        view.showMapWithSelectedCity();
    }

    @Override
    public void onCityInfoClicked(CityData cityData) {
        citiesScreenInteractor.setCurrentSelectedCity(cityData);
        view.showCityInfoForSelectedCity();
    }

    @Override
    public void retry() {
        retryObservableEmitter.onNext(new Object());
    }

    private Observable<String> preProcessInputString(String searchString) {
        return Observable.fromCallable(() -> searchString.trim())
            .flatMap(trimmedString -> {
                if(trimmedString.isEmpty()) {
                    view.clearList();
                    return Observable.never();
                } else {
                    return Observable.just(trimmedString);
                }
            })
            .subscribeOn(schedulerProvider.main());
    }

    private Observable<PagedList<CityData>> buildRequestObservable(String searchString) {
        return Observable.fromCallable(() ->
                new CitiesSearchDataSourceFactory(searchString, retryObservable, this, schedulerProvider,citiesSearchInteractor, compositeDisposable, initialPageSizeFactor)
            )
            .flatMap(citiesSearchDataSourceFactory ->
                new RxPagedListBuilder<>(citiesSearchDataSourceFactory, pageListConfig.get())
                .setNotifyScheduler(schedulerProvider.main())
                .setFetchScheduler(schedulerProvider.io())
                .buildObservable()
            )
            .doOnSubscribe(disposable -> XLog.i(TAG, "CitiesSearchPresenterImpl.buildRequestObservable(): Subscribe. "))
            .doOnNext(result -> XLog.i(TAG, "CitiesSearchPresenterImpl.buildRequestObservable(): Success. result=" + result))
            .doOnError(throwable -> XLog.w(TAG, "CitiesSearchPresenterImpl.buildRequestObservable(): Error", throwable));
    }

    @Override
    public void onRequestStarted() {
        view.showProgress();
    }

    @Override
    public void onResult(CitiesSearchResultCode resultCode) {
        if (resultCode != CitiesSearchResultCode.OK) {
            view.showError();
        }

        view.hideProgress();
    }

    @Override
    public void onAboutAppClicked() {
        view.showAboutApp();
    }

    @Override
    public void onDestroyView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}

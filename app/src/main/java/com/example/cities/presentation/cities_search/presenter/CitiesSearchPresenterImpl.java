package com.example.cities.presentation.cities_search.presenter;

import android.util.Log;

import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.presentation.cities_search.presenter.recycler.CitiesSearchDataSourceFactory;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CitiesSearchPresenterImpl implements CitiesSearch.Presenter {

    private final CitiesSearch.View view;
    private final SchedulerProvider schedulerProvider;
    private final CitiesSearchInteractor citiesSearchInteractor;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ObservableEmitter<String> performSearchObservableEmitter = null;
    private Observable<String> performSearchObservable = Observable.create( emitter -> performSearchObservableEmitter = emitter );

    private ObservableEmitter<String> submitSearchObservableEmitter = null;
    private Observable<String> submitSearchObservable = Observable.create( emitter ->  submitSearchObservableEmitter = emitter );

    private ObservableEmitter<Object> retryObservableEmitter = null;
    private Observable<Object> retryObservable = Observable.create( emitter ->  retryObservableEmitter = emitter );


    private int initialPageSizeFactor = DEFAULT_INITIAL_PAGE_SIZE_FACTOR;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private long performSearchTimeoutMillis = DEFAULT_PERFORM_SEARCH_TIMEOUT_MILLIS;

    private static final String TAG = "CitiesSearchPresenter";
    private static final long DEFAULT_PERFORM_SEARCH_TIMEOUT_MILLIS = 500L;

    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int DEFAULT_INITIAL_PAGE_SIZE_FACTOR = 3;

    private PagedList.Config pageListConfig = new PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(initialPageSizeFactor * pageSize)
            .setEnablePlaceholders(false)
            .build();

    @Inject
    public CitiesSearchPresenterImpl(CitiesSearch.View view,
                                     SchedulerProvider schedulerProvider,
                                     CitiesSearchInteractor citiesSearchInteractor) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.citiesSearchInteractor = citiesSearchInteractor;
    }


    @Override
    public void onViewReady() {
        Disposable searchTextDisposable = Observable.merge(submitSearchObservable, performSearchObservable.debounce(performSearchTimeoutMillis, TimeUnit.MILLISECONDS))
                .flatMap(searchString ->preProcessInputString(searchString))
                .doOnNext(searchString -> Log.i(TAG, "Filtered search string: $searchString"))
                .distinctUntilChanged()
                .switchMap(searchString -> performRequest(searchString) )
                .subscribeOn(schedulerProvider.main())
                .subscribe(cityDataPagedList -> {
                        view.updateCityList(cityDataPagedList);
                }, throwable -> {
                        Log.w(TAG, "Error in search text chain", throwable);
                });

        compositeDisposable.add(searchTextDisposable);

        view.hideError();
        view.hideProgress();
    }

    @Override
    public void onSearchTextChanged(String searchText) {
        performSearchObservableEmitter.onNext(searchText);
    }

    @Override
    public void onSearchTextSubmitted(String searchText) {
        submitSearchObservableEmitter.onNext(searchText);
    }

    @Override
    public void retry() {
        retryObservableEmitter.onNext(new Object());
    }

    private Observable<String> preProcessInputString(String searchString) {
        return Observable.fromCallable(() -> searchString.isEmpty() )
            .flatMap(isEmpty -> {
                if(isEmpty) {
                    view.clearList();
                    return Observable.never();
                } else {
                    return Observable.just(searchString);
                }
            })
            .subscribeOn(schedulerProvider.main());
    }

    private Observable<PagedList<CityData>> performRequest(String searchString) {
        return Observable.fromCallable(() ->
                new CitiesSearchDataSourceFactory(searchString, retryObservable, this, schedulerProvider,citiesSearchInteractor, compositeDisposable, initialPageSizeFactor)
            )
            .flatMap(citiesSearchDataSourceFactory ->
                new RxPagedListBuilder<>(citiesSearchDataSourceFactory, pageListConfig)
                .setNotifyScheduler(schedulerProvider.main())
                .setFetchScheduler(schedulerProvider.io())
                .buildObservable()
            )
            .doOnSubscribe(disposable -> Log.i(TAG, "CitiesSearchPresenterImpl.performRequest(): Subscribe. "))
            .doOnNext(result -> Log.i(TAG, "CitiesSearchPresenterImpl.performRequest(): Success. result=" + result))
            .doOnError(throwable -> Log.w(TAG, "CitiesSearchPresenterImpl.performRequest(): Error", throwable));
    }

    @Override
    public void onRequestStarted() {
        view.showProgress();
    }

    @Override
    public void onResult(CitiesSearchResultCode resultCode) {
        view.hideProgress();

    }
}
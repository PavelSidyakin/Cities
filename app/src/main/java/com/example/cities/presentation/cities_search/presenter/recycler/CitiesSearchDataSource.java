package com.example.cities.presentation.cities_search.presenter.recycler;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class CitiesSearchDataSource extends PageKeyedDataSource<Integer, CityData> {

    private final String searchText;
    private final CitiesSearch.Presenter citiesSearchPresenter;
    private final SchedulerProvider schedulerProvider;
    private final CitiesSearchInteractor citiesSearchInteractor;
    private final CompositeDisposable compositeDisposable;
    private final int initialPageSizeFactor;

    private Action retryAction;

    private static final String TAG = "CitiesSearchDataSource";

    CitiesSearchDataSource(String searchText,
                           Observable<Object> retryObservable, CitiesSearch.Presenter citiesSearchPresenter,
                           SchedulerProvider schedulerProvider,
                           CitiesSearchInteractor citiesSearchInteractor,
                           CompositeDisposable compositeDisposable, int initialPageSizeFactor) {
        this.searchText = searchText;
        this.citiesSearchPresenter = citiesSearchPresenter;
        this.schedulerProvider = schedulerProvider;
        this.citiesSearchInteractor = citiesSearchInteractor;
        this.compositeDisposable = compositeDisposable;
        this.initialPageSizeFactor = initialPageSizeFactor;

        compositeDisposable.add(retryObservable.subscribe(o -> {
                if (retryAction != null) {
                    retryAction.run();
                }
            }
        ));
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CityData> callback) {
        Disposable loadInitialDisposable = Completable.fromAction(() -> citiesSearchPresenter.onRequestStarted() )
            .andThen(Single.defer(() -> citiesSearchInteractor.requestCities(searchText, 0, params.requestedLoadSize) ))
            .subscribeOn(schedulerProvider.main())
            .observeOn(schedulerProvider.main())
            .doOnSubscribe(disposable -> XLog.i(TAG, "CitiesSearchDataSource.loadInitial(): Subscribe. "))
            .doOnSuccess(result -> XLog.i(TAG, "CitiesSearchDataSource.loadInitial(): Success. result=" + result))
            .doOnError(throwable -> XLog.w(TAG, "CitiesSearchDataSource.loadInitial(): Error", throwable))
            .subscribe (citiesSearchResult -> {
                if (citiesSearchResult.getResultCode() == CitiesSearchResultCode.OK) {
                    callback.onResult(citiesSearchResult.getResultData().getCityDataList(),
                            null, initialPageSizeFactor + 1);
                } else {
                    setRetryAction(() -> loadInitial(params, callback));
                }
                citiesSearchPresenter.onResult(citiesSearchResult.getResultCode());
            }, throwable -> {
                setRetryAction(() -> loadInitial(params, callback));
                citiesSearchPresenter.onResult(CitiesSearchResultCode.ERROR);
        });

        compositeDisposable.add(loadInitialDisposable);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CityData> callback) {
        Disposable loadAfterDisposable = Completable.fromAction(() -> citiesSearchPresenter.onRequestStarted() )
                .andThen(Single.defer(() -> citiesSearchInteractor.requestCities(searchText, params.key, params.requestedLoadSize) ))
                .subscribeOn(schedulerProvider.main())
                .observeOn(schedulerProvider.main())
                .doOnSubscribe(disposable -> XLog.i(TAG, "CitiesSearchDataSource.loadAfter(): Subscribe. "))
                .doOnSuccess(result -> XLog.i(TAG, "CitiesSearchDataSource.loadAfter(): Success. result=" + result))
                .doOnError(throwable -> XLog.w(TAG, "CitiesSearchDataSource.loadAfter(): Error", throwable))
                .subscribe (citiesSearchResult -> {
                    if (citiesSearchResult.getResultCode() == CitiesSearchResultCode.OK) {
                        callback.onResult(citiesSearchResult.getResultData().getCityDataList(),
                                params.key + 1);
                    } else {
                        setRetryAction(() -> loadAfter(params, callback));
                    }
                    citiesSearchPresenter.onResult(citiesSearchResult.getResultCode());
                }, throwable -> {
                    setRetryAction(() -> loadAfter(params, callback));
                    citiesSearchPresenter.onResult(CitiesSearchResultCode.ERROR);
                });

        compositeDisposable.add(loadAfterDisposable);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CityData> callback) {
    }

    private void setRetryAction(Action action) {
        if (action != null) {
            retryAction = action;
        }
    }
}

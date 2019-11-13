package com.example.cities.presentation.cities.cities_search;

import androidx.paging.PagedList;

import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.data.CityData;

public interface CitiesSearch {

    interface Presenter {
        void onViewReady();

        void onSearchTextChanged(String searchText);
        void onSearchTextSubmitted(String searchText);

        void retry();

        void onRequestStarted();
        void onResult(CitiesSearchResultCode resultCode);

    }

    interface View {

        void updateCityList(PagedList<CityData> cityList);
        void clearList();

        void showError();
        void hideError();

        void showProgress();
        void hideProgress();

    }
}

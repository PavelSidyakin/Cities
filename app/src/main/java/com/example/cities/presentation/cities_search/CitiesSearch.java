package com.example.cities.presentation.cities_search;

import androidx.paging.PagedList;

import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.data.CityData;

public interface CitiesSearch {

    interface Presenter {
        void onViewReady();

        void onSearchTextChanged(String searchText);
        void onSearchTextSubmitted(String searchText);

        void onCityClicked(CityData cityData);

        void retry();

        void onRequestStarted();
        void onResult(CitiesSearchResultCode resultCode);

        void onDestroyView();
    }

    interface View {

        void updateCityList(PagedList<CityData> cityList);
        void clearList();

        void setSearchText(String currentSearchText);

        void showMapWithSelectedCity();

        void showError();
        void hideError();

        void showProgress();
        void hideProgress();

    }
}

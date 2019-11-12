package com.example.cities.presentation.cities.cities_search;

import androidx.paging.PagedList;

public interface CitiesSearch {

    interface Model {

    }

    interface Presenter {

    }

    interface View {

        void updateCityList(PagedList cityList);

        void showError();
        void hideError();

        void showProgress();
        void hideProgress();

    }
}

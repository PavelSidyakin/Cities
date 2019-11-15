package com.example.cities.presentation.city_map;

import com.example.cities.model.data.CityCoordinates;

public interface CityMap {

    interface Presenter {
        void onViewReady();

        void onBackClicked();

        void onDestroyView();

    }

    interface View {

        void navigateToCity(CityCoordinates coordinates);

        void addCityMarker(CityCoordinates coordinates, String title);

        void showCitiesSearchScreen();


    }
}

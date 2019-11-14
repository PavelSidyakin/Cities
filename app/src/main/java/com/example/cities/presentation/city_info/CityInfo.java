package com.example.cities.presentation.city_info;

import com.example.cities.model.data.CityCoordinates;

public interface CityInfo {

    interface Presenter {
        void onViewReady();
    }

    interface View {

        void setCityName(String cityName);
        void setCountryName(String countryName);
        void setCoordinates(CityCoordinates coordinates);

    }
}

package com.example.cities.presentation.city_map;

import com.google.android.gms.maps.model.LatLng;

public interface CityMap {

    interface Presenter {
        void onViewReady();

        void onDestroyView();

    }

    interface View {

        void goToCity(LatLng coordinates, String title);

    }
}

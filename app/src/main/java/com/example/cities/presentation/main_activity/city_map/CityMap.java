package com.example.cities.presentation.main_activity.city_map;

import com.google.android.gms.maps.model.LatLng;

public interface CityMap {

    interface Model {

    }

    interface Presenter {

    }

    interface View {

        void setMarkerPos(LatLng coordinates, String title, String tag);

    }
}

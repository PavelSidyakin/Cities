package com.example.cities.presentation.city_map.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cities.R;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.presentation.city_map.CityMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CityMapFragment extends DaggerFragment implements CityMap.View, OnMapReadyCallback {

    public static final String FRAGMENT_TAG = "CityMapFragment";

    @Inject
    CityMap.Presenter presenter;

    private GoogleMap googleMap;

    private static final float DEFAULT_ZOOM = 15f;


    @Inject
    public CityMapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_map_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_googlemap_city_map);
        SupportMapFragment mapFragment = (SupportMapFragment) fragment;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void navigateToCity(CityCoordinates coordinates) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertCityCoordinates2LatLng(coordinates), DEFAULT_ZOOM));
    }

    @Override
    public void addCityMarker(CityCoordinates coordinates, String title) {
        googleMap.addMarker(new MarkerOptions()
                .position(convertCityCoordinates2LatLng(coordinates))
                .title(title))
            .showInfoWindow();
    }

    private LatLng convertCityCoordinates2LatLng(CityCoordinates coordinates) {
        return new LatLng(coordinates.getLat(), coordinates.getLon());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        presenter.onViewReady();
    }
}

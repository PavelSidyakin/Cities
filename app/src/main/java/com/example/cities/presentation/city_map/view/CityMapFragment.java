package com.example.cities.presentation.city_map.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cities.R;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.presentation.city_map.CityMap;
import com.example.cities.presentation.main_screen.MainScreen;
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

    @Inject
    MainScreen.View mainView;

    private GoogleMap googleMap;

    private static final float DEFAULT_ZOOM = 10f;

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

        showActionBarIfNeeded();

        view.findViewById(R.id.image_view_toolbar_back_city_map).setOnClickListener(v -> presenter.onBackClicked());
        view.findViewById(R.id.text_view_toolbar_back_city_map).setOnClickListener(v -> presenter.onBackClicked());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_googlemap_city_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            showActionBarIfNeeded();
        }
    }

    private void showActionBarIfNeeded() {
        Activity activity = getActivity();

        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(getView().findViewById(R.id.toolbar_city_map));
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {

                int displayMode = getResources().getConfiguration().orientation;

                boolean isLandscape = displayMode == Configuration.ORIENTATION_LANDSCAPE;

                if (isLandscape) {
                    actionBar.hide();
                } else {
                    actionBar.setDisplayShowTitleEnabled(false);
                    actionBar.show();
                }
            }
        }

    }

    @Override
    public void navigateToCity(CityCoordinates coordinates) {
        if (googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertCityCoordinates2LatLng(coordinates), DEFAULT_ZOOM));
        }
    }

    @Override
    public void addCityMarker(CityCoordinates coordinates, String title) {
        googleMap.addMarker(new MarkerOptions()
                .position(convertCityCoordinates2LatLng(coordinates))
                .title(title))
            .showInfoWindow();
    }

    @Override
    public void showCitiesSearchScreen() {
        mainView.showCitiesSearchScreen();
    }

    private LatLng convertCityCoordinates2LatLng(CityCoordinates coordinates) {
        return new LatLng(coordinates.getLat(), coordinates.getLng());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        presenter.onViewReady();
    }

}

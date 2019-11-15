package com.example.cities.presentation.city_info.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cities.R;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.presentation.city_info.CityInfo;

import javax.inject.Inject;

import dagger.android.support.DaggerDialogFragment;

public class CityInfoFragment extends DaggerDialogFragment implements CityInfo.View {

    public static final String FRAGMENT_TAG = "CityInfoFragment";

    @Inject
    CityInfo.Presenter presenter;

    private TextView textViewCity;
    private TextView textViewCountry;
    private TextView textViewLatitude;
    private TextView textViewLongitude;

    @Inject
    public CityInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        textViewCity = view.findViewById(R.id.text_view_city_name_city_info);
        textViewCountry = view.findViewById(R.id.text_view_city_country_city_info);
        textViewLatitude = view.findViewById(R.id.text_view_city_latitude_city_info);
        textViewLongitude = view.findViewById(R.id.text_view_city_longitude_city_info);

        presenter.onViewReady();
    }

    @Override
    public void setCityName(String cityName) {
        textViewCity.setText(cityName);
    }

    @Override
    public void setCountryName(String countryName) {
        textViewCountry.setText(getString(R.string.city_info_country_mask, countryName));
    }

    @Override
    public void setCoordinates(CityCoordinates coordinates) {
        textViewLatitude.setText(getString(R.string.city_info_latitude_mask, coordinates.getLat()));
        textViewLongitude.setText(getString(R.string.city_info_longitude_mask, coordinates.getLng()));
    }
}

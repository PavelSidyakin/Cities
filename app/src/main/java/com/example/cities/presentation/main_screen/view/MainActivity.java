package com.example.cities.presentation.main_screen.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cities.R;
import com.example.cities.presentation.cities_search.view.CitiesSearchFragment;
import com.example.cities.presentation.city_map.view.CityMapFragment;
import com.example.cities.presentation.main_screen.MainScreen;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainScreen.View {

    @Inject
    MainScreen.Presenter presenter;

    private AlertDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new AlertDialog.Builder(this)
            .setView(R.layout.main_progress_dialog_initialization)
            .setCancelable(false)
            .create();

        presenter.onViewReady();

    }

    @Override
    protected void onDestroy() {
        // TODO: remove it. Added to test activity destroying
        //finish();

        presenter.onDestroyView();

        super.onDestroy();
    }

    @Override
    public void showLoadingProgress() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showCitiesSearchScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_container_activity_main, new CitiesSearchFragment());

        fragmentTransaction.commit();
    }

    @Override
    public void showCityMapScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(CitiesSearchFragment.FRAGMENT_TAG);

        fragmentTransaction.replace(R.id.frame_container_activity_main, new CityMapFragment());

        fragmentTransaction.commit();
    }
}

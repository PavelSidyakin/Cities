package com.example.cities.presentation.main_screen.view;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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

    private CitiesSearchFragment citiesSearchFragment;
    private CityMapFragment cityMapFragment;

    private MainActivityDelegate mainActivityDelegate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cleanUpOnConfigChanges();
        initUi();
    }

    private void initUi() {
        int displayMode = getResources().getConfiguration().orientation;

        boolean isPortrait = displayMode == Configuration.ORIENTATION_PORTRAIT;

        setContentView(isPortrait ? R.layout.activity_main : R.layout.activity_main_land);

        progressDialog = new AlertDialog.Builder(this)
                .setView(R.layout.main_progress_dialog_initialization)
                .setCancelable(false)
                .create();

        citiesSearchFragment = (CitiesSearchFragment) getSupportFragmentManager()
                .findFragmentById(isPortrait ? R.id.fragment_cities_search_main_activity : R.id.fragment_cities_search_main_activity_land);
        cityMapFragment = (CityMapFragment) getSupportFragmentManager()
                .findFragmentById(isPortrait ? R.id.fragment_city_map_main_activity : R.id.fragment_city_map_main_activity_land);

        mainActivityDelegate = isPortrait ?
                new MainActivityPortraitDelegate()
                : new MainActivityLandscapeDelegate();

        mainActivityDelegate.onCreateFinalActions();
    }

    private void cleanUpOnConfigChanges() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void hideAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.hide(citiesSearchFragment);
        fragmentTransaction.hide(cityMapFragment);

        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
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
        mainActivityDelegate.showCitiesSearchScreen();
    }

    @Override
    public void showCityMapScreen() {
        mainActivityDelegate.showCityMapScreen();
    }

    @Override
    public void onBackPressed() {
        mainActivityDelegate.onBackPressed();
    }

    private interface MainActivityDelegate {

        void onCreateFinalActions();

        void showCitiesSearchScreen();

        void showCityMapScreen();

        void onBackPressed();

    }

    private class MainActivityPortraitDelegate implements MainActivityDelegate {

        @Override
        public void onCreateFinalActions() {
            if (!citiesSearchFragment.isHidden() && !cityMapFragment.isHidden()) {
                // Initial start. Hide all.
                hideAllFragments();
            }
            presenter.onViewReady();
        }

        @Override
        public void showCitiesSearchScreen() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.hide(cityMapFragment);
            fragmentTransaction.show(citiesSearchFragment);

            fragmentTransaction.commit();
        }

        @Override
        public void showCityMapScreen() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.hide(citiesSearchFragment);
            fragmentTransaction.show(cityMapFragment);

            fragmentTransaction.commit();
        }

        @Override
        public void onBackPressed() {
            if (!cityMapFragment.isHidden() && citiesSearchFragment.isHidden()) {
                showCitiesSearchScreen();
            } else {
                MainActivity.super.onBackPressed();
            }
        }
    }

    private class MainActivityLandscapeDelegate implements MainActivityDelegate {

        @Override
        public void onCreateFinalActions() {
            presenter.onViewReady();
        }

        @Override
        public void showCitiesSearchScreen() {
            // Do nothing. Both screens are shown always
        }

        @Override
        public void showCityMapScreen() {
            // Do nothing. Both screens are shown always
        }

        @Override
        public void onBackPressed() {
            // Just hide screen
            MainActivity.super.onBackPressed();
        }
    }
}

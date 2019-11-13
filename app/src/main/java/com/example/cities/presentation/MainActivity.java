package com.example.cities.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cities.R;
import com.example.cities.presentation.cities.cities_search.view.CitiesSearchFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    //@Inject
    CitiesSearchFragment citiesSearchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_container_activity_main, new CitiesSearchFragment());

        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

}

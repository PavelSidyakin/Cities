package com.example.cities.data;

import com.example.cities.R;
import com.example.cities.di.PerFeature;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.CitiesRepository;
import com.example.cities.model.data.CitiesData;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@PerFeature
public class CitiesRepositoryImpl implements CitiesRepository {

    private final ApplicationProvider applicationProvider;
    private final SchedulerProvider schedulerProvider;

    @Inject
    CitiesRepositoryImpl(ApplicationProvider applicationProvider, SchedulerProvider schedulerProvider) {
        this.applicationProvider = applicationProvider;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<CitiesData> getCitiesData() {
        return Single.fromCallable(() -> applicationProvider.getApplicationContext())
                .map(context -> context.getResources())
                .map(resources -> resources.openRawResource(R.raw.cities))
                .map(inputStream -> new InputStreamReader(inputStream))
                .map(inputStreamReader -> new JsonReader(inputStreamReader))
                .map(jsonReader -> new Gson().<List<CityData>>fromJson(jsonReader, new TypeToken<List<CityData>>() {}.getType()))
                .map(cityDataList -> new CitiesData(cityDataList))
                .subscribeOn(schedulerProvider.io());
    }
}

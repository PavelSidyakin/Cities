package com.example.cities.di.screen.main_screen;

import com.example.cities.data.CitiesScreenRepositoryImpl;
import com.example.cities.di.screen.cities_search.CitiesSearchFragmentScope;
import com.example.cities.di.cities_search.CitiesSearchModule;
import com.example.cities.di.screen.cities_search.CitiesSearchFragmentModule;
import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.domain.cities_search.CitiesScreenInteractorImpl;
import com.example.cities.domain.cities_search.CitiesScreenRepository;
import com.example.cities.presentation.cities_search.view.CitiesSearchFragment;
import com.example.cities.presentation.main_screen.MainScreen;
import com.example.cities.presentation.main_screen.presenter.MainScreenPresenterImpl;
import com.example.cities.presentation.main_screen.view.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @CitiesSearchFragmentScope
    @ContributesAndroidInjector(modules = { CitiesSearchFragmentModule.class, CitiesSearchModule.class})
    abstract CitiesSearchFragment bindCitiesSearchFragment();

    @Binds
    @MainActivityScope
    abstract MainScreen.Presenter provideMainScreenPresenter(MainScreenPresenterImpl mainScreenPresenter);

    @Binds
    @MainActivityScope
    abstract MainScreen.View provideMainScreenView(MainActivity mainActivity);

}

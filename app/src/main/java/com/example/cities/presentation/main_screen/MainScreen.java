package com.example.cities.presentation.main_screen;

public interface MainScreen {

    interface Presenter {
        void onViewReady();
        void onDestroyView();
    }

    interface View {

        void showLoadingProgress();
        void hideLoadingProgress();

        void showCitiesSearchScreen();
        void showCityMapScreen();

        void showCityInfoScreen();

    }
}

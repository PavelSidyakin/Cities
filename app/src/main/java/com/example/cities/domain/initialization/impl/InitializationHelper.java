package com.example.cities.domain.initialization.impl;

public interface InitializationHelper {

    /**
     * Starts initialization. Shouldn't be called from anywhere except from {@link com.example.cities.TheApplication#onCreate()}
     *
     * Use instead {@link com.example.cities.domain.initialization.InitializationInteractor#observeInitializationCompleteness()}
     */
    void launchInitialization();

}

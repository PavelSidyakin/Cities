package com.example.cities.presentation.main_screen.view;


import android.widget.AutoCompleteTextView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.cities.R;
import com.example.cities.di.DiMocks;
import com.example.cities.domain.cities_search.CitiesRepository;
import com.example.cities.model.data.CitiesData;
import com.example.cities.model.data.CityCoordinates;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.ProgressBarUtils;

import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.cities.utils.CustomViewAction.clickChildViewWithId;
import static com.example.cities.utils.ViewShownIdlingResource.waitViewIsShown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityTest {

    private static List<CityData> citiesForTest = Collections.singletonList(
            new CityData("aaaa", "cccc", new CityCoordinates(11.99999, 22.33333), 1)
    );

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    public static void setUpBeforeAppCreation() {
        CitiesRepository citiesRepository = mock(CitiesRepository.class);
        DiMocks.INSTANCE.citiesRepository = citiesRepository;

        // when
        when(citiesRepository.getCitiesData()).thenReturn(Single.just(new CitiesData(citiesForTest)));
    }

    @Test
    public void onStart_MainActivity() throws InterruptedException {

        // wait until loading is completed
        ProgressBarUtils.waitUntilGoneProgressBar(R.id.progress_bar_progress_dialog_initialization, 10000);

        // click on the search view
        onView(withId(R.id.search_view_cities_search)).perform(click());

        // Type a first letter in the search view
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(citiesForTest.get(0).getName().substring(0, 1)));

        // Wait recycler view is updated.
        // Here should be a better way...
        Thread.sleep(500);

        // Click on city info button
        onView(withId(R.id.recycler_list_cities_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                            clickChildViewWithId(R.id.image_view_info_list_item)
                        )
                );

        // Wait for info screen is shown
        waitViewIsShown(withId(R.id.container_city_info));

        // Info should correspond information from assets
        CityData expectedCityData = citiesForTest.get(0);
        onView(withId(R.id.text_view_city_name_city_info)).check(matches(withText(expectedCityData.getName())));
        onView(withId(R.id.text_view_city_longitude_city_info)).check(matches(withText(
                getApplicationContext().getString(R.string.city_info_longitude_mask, expectedCityData.getCityCoordinates().getLng()))));
        onView(withId(R.id.text_view_city_latitude_city_info)).check(matches(withText(
                getApplicationContext().getString(R.string.city_info_latitude_mask, expectedCityData.getCityCoordinates().getLat()))));
    }
}
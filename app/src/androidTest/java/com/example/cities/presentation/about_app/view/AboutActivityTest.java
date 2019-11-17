package com.example.cities.presentation.about_app.view;

import android.content.res.AssetManager;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.cities.R;
import com.example.cities.model.data.AboutInfo;
import com.example.cities.utils.ProgressBarUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class AboutActivityTest {

    private static final String ASSETS_ABOUT_INFO_FILE_NAME = "aboutInfo.json";

    @Rule
    public ActivityScenarioRule<AboutActivity> activityScenarioRule
            = new ActivityScenarioRule<>(AboutActivity.class);

    @Test
    public void onActivityStart_AboutActivity() {

        // Progress bar should be shown
        ProgressBarUtils.waitForExistProgressBar(R.id.progress_bar_about_activity, 10000);
        ProgressBarUtils.waitUntilGoneProgressBar(R.id.progress_bar_about_activity, 10000);

        // After request is completed, info container should be displayed
        onView(withId(R.id.layout_info_container_about_activity)).check(matches(isDisplayed()));

        // Info should correspond information from assets
        AboutInfo expectedInfo = parseAboutInfo(getAboutInfoFromAssets());
        onView(withId(R.id.text_view_company_name_about_activity)).check(matches(withText(expectedInfo.getCompanyName())));
        onView(withId(R.id.text_view_company_city_about_activity)).check(matches(withText(expectedInfo.getCompanyCity())));
        onView(withId(R.id.text_view_company_postal_about_activity)).check(matches(withText(expectedInfo.getCompanyPostal())));
        onView(withId(R.id.text_view_about_info_about_activity)).check(matches(withText(expectedInfo.getAboutInfo())));
        onView(withId(R.id.text_view_company_address_about_activity)).check(matches(withText(expectedInfo.getCompanyAddress())));
    }

    private AboutInfo parseAboutInfo(String aboutInfoJson) {
        return new Gson().fromJson(new JsonReader(new StringReader(aboutInfoJson)),
                new TypeToken<AboutInfo>() {}.getType());
    }

    private String getAboutInfoFromAssets() {
        try {
            AssetManager manager = getApplicationContext().getAssets();
            InputStream file = manager.open(ASSETS_ABOUT_INFO_FILE_NAME);
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();
            return new String(formArray);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to get about info from assets", ex);
        }
    }

}
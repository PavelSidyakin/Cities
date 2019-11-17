package com.example.cities.utils;

import androidx.annotation.IdRes;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class ProgressBarUtils {

    public static void waitUntilGoneProgressBar(@IdRes int resId, long timeoutMs) {
        progressBar(resId).waitUntilGone(timeoutMs);
    }

    public static void waitForExistProgressBar(@IdRes int resId, long timeoutMs) {
        progressBar(resId).waitForExists(timeoutMs);
    }

    private static UiObject progressBar(@IdRes int resId) {
        return uiObjectWithId(resId);
    }

    private static UiObject uiObjectWithId(@IdRes int resId) {
        String resourceId = getApplicationContext().getResources().getResourceName(resId);
        UiSelector selector = new UiSelector().resourceId(resourceId);
        return UiDevice.getInstance(getInstrumentation()).findObject(selector);
    }
}

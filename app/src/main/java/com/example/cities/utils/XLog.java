package com.example.cities.utils;

import android.util.Log;

public final class XLog {

    private static boolean enabled = true; 
    
    public static void i(String tag, String message, Throwable throwable) {
        if (enabled) {
            Log.i(tag, message, throwable);
        }
    }

    public static void i(String tag, String message) {
        if (enabled) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message, Throwable throwable) {
        if (enabled) {
            Log.w(tag, message, throwable);
        }
    }

    public static void w(String tag, String message) {
        if (enabled) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable throwable) {
        if (enabled) {
            Log.e(tag, message, throwable);
        }
    }

    public static void e(String tag, String message) {
        if (enabled) {
            Log.e(tag, message);
        }
    }
}

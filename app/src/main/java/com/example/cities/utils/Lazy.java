package com.example.cities.utils;

import androidx.core.util.Supplier;

// Taken from here: https://4comprehension.com/leveraging-lambda-expressions-for-lazy-evaluation-in-java/
public class Lazy<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private volatile T value;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = supplier.get();
                }
            }
        }
        return value;
    }
}
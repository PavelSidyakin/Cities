package com.example.cities.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler main();

    Scheduler io();

    Scheduler computation();

}

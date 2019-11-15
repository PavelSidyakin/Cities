package com.example.cities.utils.rx;

import io.reactivex.Scheduler;

/**
 * Wrapper for RxJava schedulers
 *
 */
public interface SchedulerProvider {

    Scheduler main();

    Scheduler io();

    Scheduler computation();

}

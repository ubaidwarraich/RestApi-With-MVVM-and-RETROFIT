package com.codingwithmitch.foodrecipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    // add extra functionality to executors
    //Scheduled Executor service is a executor service that can schedule commands to run after a given delay
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService NetworkIO() {
        return mNetworkIO;
    }
}

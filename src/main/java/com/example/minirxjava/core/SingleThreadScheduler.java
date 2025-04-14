package com.example.minirxjava.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadScheduler implements Scheduler {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void execute(Runnable task) {
        executorService.submit(task);
    }
}

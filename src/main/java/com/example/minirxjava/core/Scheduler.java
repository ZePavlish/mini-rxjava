package com.example.minirxjava.core;

public interface Scheduler {
    void execute(Runnable task);
}

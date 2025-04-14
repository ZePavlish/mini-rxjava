package com.example.minirxjava.core;

import java.util.ArrayList;
import java.util.List;

public class TestObserver<T> implements Observer<T> {
    private final List<T> values = new ArrayList<>();
    private Throwable error;
    private boolean completed = false;

    @Override
    public void onNext(T item) {
        values.add(item);
    }

    @Override
    public void onError(Throwable t) {
        error = t;
    }

    @Override
    public void onComplete() {
        completed = true;
    }

    public List<T> getValues() {
        return values;
    }

    public Throwable getError() {
        return error;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isErrorOccurred() {
        return error != null;
    }
}

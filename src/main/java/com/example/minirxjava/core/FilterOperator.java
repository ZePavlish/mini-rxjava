package com.example.minirxjava.core;

import java.util.function.Predicate;

public class FilterOperator<T> implements Observer<T> {
    private final Observer<T> downstream;
    private final Predicate<T> predicate;

    public FilterOperator(Observer<T> downstream, Predicate<T> predicate) {
        this.downstream = downstream;
        this.predicate = predicate;
    }

    @Override
    public void onNext(T item) {
        if (predicate.test(item)) {
            downstream.onNext(item);
        }
    }

    @Override
    public void onError(Throwable t) {
        downstream.onError(t);
    }

    @Override
    public void onComplete() {
        downstream.onComplete();
    }
}

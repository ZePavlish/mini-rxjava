package com.example.minirxjava.core;

import java.util.function.Function;

public class MapOperator<T, R> implements Observer<T> {
    private final Observer<R> downstream;
    private final Function<T, R> mapper;

    public MapOperator(Observer<R> downstream, Function<T, R> mapper) {
        this.downstream = downstream;
        this.mapper = mapper;
    }

    @Override
    public void onNext(T item) {
        // Преобразуем элемент с помощью mapper и передаем результат
        R result = mapper.apply(item);
        downstream.onNext(result);
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

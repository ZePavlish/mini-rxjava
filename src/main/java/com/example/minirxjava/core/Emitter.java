package com.example.minirxjava.core;

public interface Emitter<T> {
    void onNext(T item);
    void onError(Throwable t);
    void onComplete();
}

package com.example.minirxjava.core;

public interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter);
}

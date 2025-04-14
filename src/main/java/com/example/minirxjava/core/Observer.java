package com.example.minirxjava.core;

public interface Observer<T> {
    void onNext(T item);    // Получение элемента потока
    void onError(Throwable t);  // Обработка ошибок
    void onComplete();    // Завершение потока
}

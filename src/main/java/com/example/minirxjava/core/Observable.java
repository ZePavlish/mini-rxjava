package com.example.minirxjava.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Observable<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    // Метод для подписки
    public Disposable subscribe(Observer<T> observer) {
        observers.add(observer);
        return new Disposable() {
            @Override
            public void dispose() {
                observers.remove(observer);  // Удаление observer из списка при отписке
            }
        };
    }

    // Уведомление всех подписчиков о новом элементе
    public void emit(T item) {
        for (Observer<T> observer : observers) {
            observer.onNext(item);
        }
    }

    // Завершение потока
    public void complete() {
        for (Observer<T> observer : observers) {
            observer.onComplete();
        }
    }

    // Обработка ошибки
    public void error(Throwable t) {
        for (Observer<T> observer : observers) {
            observer.onError(t);
        }
    }

    // Оператор flatMap для преобразования каждого элемента в новый Observable
    public <R> Observable<R> flatMap(Function<T, Observable<R>> mapper) {
        Observable<R> result = new Observable<>();

        // Для каждого элемента потока изначального Observable
        for (Observer<T> observer : observers) {
            // Подписываемся на изменения и передаем их в новый Observable
            this.subscribe(new Observer<T>() {
                @Override
                public void onNext(T item) {
                    // Применяем mapper для каждого элемента
                    Observable<R> mappedObservable = mapper.apply(item);
                    // Подписываемся на результат mapper
                    mappedObservable.subscribe(new Observer<R>() {
                        @Override
                        public void onNext(R r) {
                            result.emit(r);  // Отправляем новый элемент в результат
                        }

                        @Override
                        public void onError(Throwable t) {
                            result.error(t);  // Обрабатываем ошибку
                        }

                        @Override
                        public void onComplete() {
                            result.complete();  // Завершаем выполнение
                        }
                    });
                }

                @Override
                public void onError(Throwable t) {
                    result.error(t);  // Передаем ошибку в новый Observable
                }

                @Override
                public void onComplete() {
                    result.complete();  // Завершаем выполнение
                }
            });
        }

        return result;
    }

}

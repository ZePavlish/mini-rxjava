package com.example.minirxjava.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Observable<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    public <R> Observable<R> map(Function<T, R> mapper) {
        Observable<R> result = new Observable<>();
        this.subscribe(new Observer<T>() {
            @Override
            public void onNext(T item) {
                result.emit(mapper.apply(item));
            }

            @Override
            public void onError(Throwable t) {
                result.error(t);
            }

            @Override
            public void onComplete() {
                result.complete();
            }
        });
        return result;
    }

    public Observable<T> filter(Predicate<T> predicate) {
        Observable<T> result = new Observable<>();
        this.subscribe(new Observer<T>() {
            @Override
            public void onNext(T item) {
                if (predicate.test(item)) {
                    result.emit(item);
                }
            }

            @Override
            public void onError(Throwable t) {
                result.error(t);
            }

            @Override
            public void onComplete() {
                result.complete();
            }
        });
        return result;
    }

    @SafeVarargs
    public static <T> Observable<T> just(T... items) {
        Observable<T> observable = new Observable<>();
        new Thread(() -> {
            for (T item : items) {
                observable.emit(item);
            }
            observable.complete();
        }).start();
        return observable;
    }

    public Disposable subscribe(Observer<T> observer) {
        observers.add(observer);
        return () -> observers.remove(observer);
    }

    public void emit(T item) {
        for (Observer<T> observer : observers) {
            observer.onNext(item);
        }
    }

    public void complete() {
        for (Observer<T> observer : observers) {
            observer.onComplete();
        }
    }

    public void error(Throwable t) {
        for (Observer<T> observer : observers) {
            observer.onError(t);
        }
    }

    public <R> Observable<R> flatMap(Function<T, Observable<R>> mapper) {
        Observable<R> result = new Observable<>();

        this.subscribe(new Observer<T>() {
            @Override
            public void onNext(T item) {
                mapper.apply(item).subscribe(new Observer<R>() {
                    public void onNext(R r) { result.emit(r); }
                    public void onError(Throwable t) { result.error(t); }
                    public void onComplete() { result.complete(); }
                });
            }

            @Override
            public void onError(Throwable t) {
                result.error(t);
            }

            @Override
            public void onComplete() {
                result.complete();
            }
        });

        return result;
    }

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        Observable<T> observable = new Observable<>();

        new Thread(() -> {
            try {
                source.subscribe(new Emitter<T>() {
                    public void onNext(T item) { observable.emit(item); }
                    public void onError(Throwable t) { observable.error(t); }
                    public void onComplete() { observable.complete(); }
                });
            } catch (Throwable e) {
                observable.error(e);
            }
        }).start();

        return observable;
    }
}

package com.example.minirxjava.core;

import org.junit.jupiter.api.Test;

class ObservableTest {

    @Test
    void observableEmitsItems() throws InterruptedException {
        TestObserver<String> observer = new TestObserver<>();

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("Hello");
            emitter.onNext("RxJava");
            emitter.onComplete();
        });

        observable.subscribe(observer);

        // Ждём поток
        Thread.sleep(100);

        observer.assertValues("Hello", "RxJava");
        observer.assertComplete();
        observer.assertNoErrors();
    }
}

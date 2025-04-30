package com.example.minirxjava.core;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestObserver<T> implements Observer<T> {

    private final List<T> received = new ArrayList<>();
    private boolean completed = false;
    private Throwable error = null;

    @Override
    public void onNext(T item) {
        received.add(item);
    }

    @Override
    public void onError(Throwable t) {
        this.error = t;
    }

    @Override
    public void onComplete() {
        this.completed = true;
    }

    public void assertValues(T... values) {
        assertArrayEquals(values, received.toArray());
    }

    public void assertComplete() {
        assertTrue(completed, "Observable did not complete");
    }

    public void assertNoErrors() {
        assertNull(error, "Observable had errors: " + error);
    }
}

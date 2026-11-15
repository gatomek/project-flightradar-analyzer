package pl.gatomek.flightradar.analyzer.application.domain.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BusCoordinator {

    private final AtomicInteger counter = new AtomicInteger(0);

    public int getCounter() {
        return counter.get();
    }

    public void setCounter(int value) {
        counter.set(value);
    }

    public void decrementCounter() {
        counter.decrementAndGet();
    }
}

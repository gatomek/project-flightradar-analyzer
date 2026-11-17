package pl.gatomek.flightradar.analyzer.application.domain.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BusCoordinator {

    private final AtomicInteger logCounter = new AtomicInteger(0);
    private final AtomicInteger flightCounter = new AtomicInteger(0);

    public int getLogCounter() {
        return logCounter.get();
    }

    public void setLogCounter(int value) {
        logCounter.set(value);
    }

    public void decrementLogCounter() {
        logCounter.decrementAndGet();
    }

    public int getFlightCounter() {
        return flightCounter.get();
    }

    public void incrementFlightCounter() {
        flightCounter.incrementAndGet();
    }

    public void decrementFlightCounter() {
        flightCounter.decrementAndGet();
    }
}

package pl.gatomek.flightradar.analyzer.application.domain.model;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BusCoordinator {

    private final AtomicInteger logCounter = new AtomicInteger(0);
    private final AtomicInteger flightCounter = new AtomicInteger(0);

    public BusCoordinator(MeterRegistry meterRegistry) {
        Gauge.builder("log_counter", logCounter::get)
                .description("Log Counter")
                .register(meterRegistry);

        Gauge.builder("flight_counter", flightCounter::get)
                .description("Flight Counter")
                .register(meterRegistry);

        Gauge.builder("total_event_counter", () -> logCounter.get() + flightCounter.get())
                .description("Total Event Counter")
                .register(meterRegistry);
    }

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

    public void addToFlightCounter(int delta) {
        flightCounter.getAndAdd(delta);
    }

    public void decrementFlightCounter() {
        flightCounter.decrementAndGet();
    }
}

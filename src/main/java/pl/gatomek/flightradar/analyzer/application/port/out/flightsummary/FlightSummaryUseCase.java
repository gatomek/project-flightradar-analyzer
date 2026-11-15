package pl.gatomek.flightradar.analyzer.application.port.out.flightsummary;

import java.util.UUID;

public interface FlightSummaryUseCase {
    void calc(UUID uuid);
}

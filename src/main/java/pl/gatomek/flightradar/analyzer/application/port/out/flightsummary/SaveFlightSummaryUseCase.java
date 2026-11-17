package pl.gatomek.flightradar.analyzer.application.port.out.flightsummary;

import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.model.FlightSummaryEntity;

public interface SaveFlightSummaryUseCase {
    void save(FlightSummaryEntity summary);
}

package pl.gatomek.flightradar.analyzer.application.domain.model;

import java.util.UUID;

public record CalcFlightSummaryMessage(UUID uuid) {

    public static CalcFlightSummaryMessage of(UUID uuid) {
        return new CalcFlightSummaryMessage(uuid);
    }
}

package pl.gatomek.flightradar.analyzer.adapter.in.rabbit.model;

public record LogToFlightEventMessage(String icao) {

    public static LogToFlightEventMessage of(String icao) {
        return new LogToFlightEventMessage(icao);
    }
}

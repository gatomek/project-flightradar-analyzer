package pl.gatomek.flightradar.analyzer.application.port.in;

public interface LogToFlightEventUseCase {
    void processLog(String icao);
}

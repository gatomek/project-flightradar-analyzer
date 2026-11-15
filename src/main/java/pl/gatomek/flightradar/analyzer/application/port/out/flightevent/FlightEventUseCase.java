package pl.gatomek.flightradar.analyzer.application.port.out.flightevent;

import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;

import java.util.List;
import java.util.UUID;

public interface FlightEventUseCase {
    Object[] findFlightByLog(Long logId, String icao);
    FlightEvent findLastFlightByIcao(String icao);
    FlightEvent findLastFlightEventByUuid(UUID uuid);
    void saveAll(List<FlightEvent> events);
}

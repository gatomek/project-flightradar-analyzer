package pl.gatomek.flightradar.analyzer.application.port.out;

import pl.gatomek.flightradar.analyzer.adapter.out.model.Event;

import java.util.List;

public interface EventServicePort {
    List<Event> getAllEventsByIcao(String icao);
}

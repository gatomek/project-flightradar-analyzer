package pl.gatomek.flightradar.analyzer.application.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class FlightEvent {
    private Long id;
    private UUID uuid;
    private String icao;
    private Long startLogId;
    private Long endLogId;
    private String comment;
    private Instant timestamp;
}

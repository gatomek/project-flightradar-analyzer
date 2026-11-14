package pl.gatomek.flightradar.analyzer.adapter.out.event.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
public class Event {
    private Long id;
    private Instant timestamp;
    private String icao;
    private String flight;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String barometricAltitude;
}

package pl.gatomek.flightradar.analyzer.adapter.out.flightevent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "flightevent", schema = "radar_eventsaver")
public class FlightEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flightevent_seq")
    @SequenceGenerator(name = "flightevent_seq", sequenceName = "flightevent_id_seq", allocationSize = 1, schema = "radar_eventsaver")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "icao", length = 20, nullable = false)
    private String icao;

    @Column(name = "startlogid")
    private Long startLogId;

    @Column(name = "endlogid")
    private Long endLogId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "timestamp")
    private Instant timestamp;
}

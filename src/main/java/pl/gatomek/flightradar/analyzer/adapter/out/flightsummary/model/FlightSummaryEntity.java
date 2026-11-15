package pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "flightsummary", schema = "radar_eventsaver")
public class FlightSummaryEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "icao", length = 20, nullable = false)
    private String icao;

    @Column(name = "startlogid")
    private Long startLogId;

    @Column(name = "endlogid")
    private Long endLogId;

    @Column(name = "logsize")
    private Integer logSize;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "comment")
    private String comment;
}

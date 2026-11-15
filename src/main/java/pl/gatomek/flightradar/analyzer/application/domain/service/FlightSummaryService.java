package pl.gatomek.flightradar.analyzer.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.service.FlightEventService;
import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.model.FlightSummaryEntity;
import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.repo.FlightSummaryRepository;
import pl.gatomek.flightradar.analyzer.adapter.out.log.service.LogService;
import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;
import pl.gatomek.flightradar.analyzer.application.domain.model.Log;
import pl.gatomek.flightradar.analyzer.application.port.out.flightsummary.FlightSummaryUseCase;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FlightSummaryService implements FlightSummaryUseCase {

    private final FlightEventService flightEventService;
    private final LogService logService;
    private final FlightSummaryRepository flightSummaryRepository;

    @Override
    public void calc(UUID uuid) {
        FlightEvent flightEvent = flightEventService.findLastFlightEventByUuid(uuid);
        String icao = flightEvent.getIcao();
        Long beginLogId = flightEvent.getStartLogId();
        Long endLogId = flightEvent.getEndLogId();

        List<Log> logs;

        if (endLogId != null) {
            logs = logService.getLogsForIcao(icao, beginLogId, endLogId);
        } else {
            logs = logService.getLogsForIcao(icao, beginLogId);
        }

        Instant firstTimestamp = logs.getFirst().getTimestamp();
        Instant lastTimestamp = logs.getLast().getTimestamp();
        int duration = (int) Duration.between(firstTimestamp, lastTimestamp).toMinutes();

        FlightSummaryEntity summary = new FlightSummaryEntity();
        summary.setUuid(uuid);
        summary.setIcao(icao);
        summary.setStartLogId(beginLogId);
        summary.setEndLogId(endLogId);
        summary.setComment(flightEvent.getComment());
        summary.setLogSize(logs.size());
        summary.setDuration(duration);

        flightSummaryRepository.save(summary);
    }
}

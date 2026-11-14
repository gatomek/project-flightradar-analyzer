package pl.gatomek.flightradar.analyzer.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;
import pl.gatomek.flightradar.analyzer.application.domain.model.Log;
import pl.gatomek.flightradar.analyzer.application.port.in.LogToFlightEventUseCase;
import pl.gatomek.flightradar.analyzer.application.port.out.event.LogUseCase;
import pl.gatomek.flightradar.analyzer.application.port.out.flightevent.FlightEventUseCase;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
class LogToFlightEventService implements LogToFlightEventUseCase {

    private final LogUseCase logService;
    private final FlightEventUseCase flightEventService;

    @Override
    public void processLog(String icao) {
        FlightEvent currFlight = flightEventService.findLastFlightByIcao(icao);
        List<Log> logs;
        Log lastLog = null;
        Set<UUID> uuids = new HashSet<>();

        if (currFlight != null) {
            Long startLogId = currFlight.getStartLogId();
            lastLog = logService.findById(startLogId).orElseThrow(RuntimeException::new);
            logs = logService.getAllLogsForIcaoAfterLogId(icao, startLogId);
            uuids.add(currFlight.getUuid());
        } else {
            logs = logService.getLogsByIcao(icao);
        }


        List<FlightEvent> flightEvts = new LinkedList<>();
        for (Log log : logs) {
            if (lastLog == null) {
                FlightEvent be = createBeginFlightEvent(log);
                currFlight = be;
                flightEvts.add(be);
                uuids.add(be.getUuid());
            } else {
                boolean sameFlight = checkLogFlightNumbers(log, lastLog) || checkLogTimestamps(log, lastLog);
                if (!sameFlight) {
                    FlightEvent ee = createEndFlightEvent(currFlight, lastLog);
                    flightEvts.add(ee);
                    uuids.add(ee.getUuid());

                    FlightEvent be = createBeginFlightEvent(log);
                    currFlight = be;
                    flightEvts.add(be);
                    uuids.add(be.getUuid());
                }
            }

            lastLog = log;
        }

        flightEventService.saveAll(flightEvts);
    }

    private FlightEvent createBeginFlightEvent(Log log) {
        FlightEvent e = new FlightEvent();
        e.setUuid(UUID.randomUUID());
        e.setStartLogId(log.getId());
        e.setIcao(log.getIcao());

        return e;
    }

    private FlightEvent createEndFlightEvent(FlightEvent flight, Log log) {
        FlightEvent e = new FlightEvent();
        e.setStartLogId(flight.getStartLogId());
        e.setUuid(flight.getUuid());

        e.setEndLogId(log.getId());
        e.setIcao(log.getIcao());

        return e;
    }

    private boolean checkLogFlightNumbers(Log l1, Log l2) {
        String flight1 = l1.getFlight();
        String flight2 = l2.getFlight();

        if (flight1 != null && flight2 != null) {
            return flight1.equals(flight2);
        }

        return false;
    }

    private boolean checkLogTimestamps(Log l1, Log l2) {
        Instant timestamp1 = l1.getTimestamp();
        Instant timestamp2 = l2.getTimestamp();
        Duration duration = Duration.between(timestamp2, timestamp1);
        long minutes = duration.toMinutes();
        return minutes < 15L;
    }
}

package pl.gatomek.flightradar.analyzer.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.domain.model.CalcFlightSummaryMessage;
import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;
import pl.gatomek.flightradar.analyzer.application.domain.model.Log;
import pl.gatomek.flightradar.analyzer.application.port.out.log.LogUseCase;
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
public class LogToFlightEventService {

    private final LogUseCase logUseCase;
    private final FlightEventUseCase flightEventUseCase;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BusCoordinator busCoordinator;

    public void processLog(String icao) {
        FlightEvent currFlight = flightEventUseCase.findLastFlightByIcao(icao);
        List<Log> logs;
        Log lastLog = null;
        Set<UUID> uuids = new HashSet<>();

        if (currFlight != null) {
            Long startLogId = currFlight.getStartLogId();
            lastLog = logUseCase.findById(startLogId).orElseThrow(RuntimeException::new);
            logs = logUseCase.getAllLogsForIcaoAfterLogId(icao, startLogId);
            uuids.add(currFlight.getUuid());
        } else {
            logs = logUseCase.getLogsByIcao(icao);
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

        flightEventUseCase.saveAll(flightEvts);

        busCoordinator.addToFlightCounter(uuids.size());
        uuids.forEach(uuid -> applicationEventPublisher.publishEvent(CalcFlightSummaryMessage.of(uuid)));
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

    private boolean checkLogFlightNumbers(Log log, Log lastLog) {
        String flight1 = log.getFlight();
        String flight2 = lastLog.getFlight();

        if (flight1 != null && flight2 != null) {
            return flight1.equals(flight2);
        }

        return false;
    }

    private boolean checkLogTimestamps(Log log, Log lastLog) {
        Instant logTimestamp = log.getTimestamp();
        Instant lastLogTimestamp = lastLog.getTimestamp();
        Duration duration = Duration.between(lastLogTimestamp, logTimestamp);
        long minutes = duration.toMinutes();
        return minutes < 15L;
    }
}

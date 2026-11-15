package pl.gatomek.flightradar.analyzer.application.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.domain.service.LogToFlightEventService;

@RequiredArgsConstructor
@Component
public class IcaoMessageListener {

    private final LogToFlightEventService logToFlightEventService;
    private final BusCoordinator busCoordinator;

    @Async
    @EventListener
    public void onApplicationEvent(String icao) {
        logToFlightEventService.processLog(icao);
        busCoordinator.decrementCounter();
    }
}

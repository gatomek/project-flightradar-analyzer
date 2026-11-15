package pl.gatomek.flightradar.analyzer.application.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.domain.service.LogToFlightEventService;

@RequiredArgsConstructor
@Component
public class IcaoMessageListener {

    private final LogToFlightEventService logToFlightEventService;
    private final BusCoordinator busCoordinator;

    @EventListener
    public void onApplicationEvent(String icao) {
        try {
            logToFlightEventService.processLog(icao);
        } finally {
            busCoordinator.decrementCounter();
        }
    }
}

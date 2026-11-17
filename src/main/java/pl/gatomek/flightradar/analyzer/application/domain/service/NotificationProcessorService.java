package pl.gatomek.flightradar.analyzer.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationProcessorUseCase;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationProcessorService implements NotificationProcessorUseCase {

    private final NotificationBufferService notificationBufferService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BusCoordinator busCoordinator;

    public void process() {
        final int logCounter = busCoordinator.getLogCounter();
        if (logCounter > 0) {
            return;
        }

        final int flightCounter = busCoordinator.getFlightCounter();
        if (flightCounter > 0) {
            return;
        }

        List<String> notifications = notificationBufferService.getNotifications();
        busCoordinator.setLogCounter(notifications.size());
        notifications.forEach(applicationEventPublisher::publishEvent);
    }
}

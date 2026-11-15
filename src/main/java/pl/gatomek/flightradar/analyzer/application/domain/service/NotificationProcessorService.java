package pl.gatomek.flightradar.analyzer.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationProcessorUseCase;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationProcessorService implements NotificationProcessorUseCase {

    private final NotificationBufferService notificationBufferService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BusCoordinator busCoordinator;

    public void process() {
        final int busCounter = busCoordinator.getCounter();
        if (busCounter > 0) {
            return;
        }

        List<String> notifications = notificationBufferService.getNotifications();
        busCoordinator.setCounter(notifications.size());
        notifications.forEach(applicationEventPublisher::publishEvent);
    }
}

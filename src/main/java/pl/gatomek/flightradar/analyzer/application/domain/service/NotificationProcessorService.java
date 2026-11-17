package pl.gatomek.flightradar.analyzer.application.domain.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.application.domain.model.BusCoordinator;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationProcessorUseCase;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

@Service
public class NotificationProcessorService implements NotificationProcessorUseCase {

    private final NotificationBufferService notificationBufferService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final BusCoordinator busCoordinator;
    private Long lastProcessingElapsed = 0L;
    private Instant lastProcessingInstant;

    public NotificationProcessorService(NotificationBufferService notificationBufferService,
                                        ApplicationEventPublisher applicationEventPublisher,
                                        BusCoordinator busCoordinator,
                                        MeterRegistry meterRegistry) {
        this.notificationBufferService = notificationBufferService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.busCoordinator = busCoordinator;

        Gauge.builder("last_flight_processing_elapsed", getCompanyCount())
                .description("Last Flight Processing elapsed")
                .register(meterRegistry);
    }

    public Supplier<Long> getCompanyCount() {
        return () -> lastProcessingElapsed;
    }

    public void process() {
        final int logCounter = busCoordinator.getLogCounter();
        if (logCounter > 0) {
            return;
        }

        final int flightCounter = busCoordinator.getFlightCounter();
        if (flightCounter > 0) {
            return;
        }

        Instant now = Instant.now();
        if (lastProcessingInstant != null) {
            lastProcessingElapsed = Duration.between(lastProcessingInstant, now).toSeconds();
        }

        lastProcessingInstant = now;

        List<String> notifications = notificationBufferService.getNotifications();
        busCoordinator.setLogCounter(notifications.size());
        notifications.forEach(applicationEventPublisher::publishEvent);
    }
}

package pl.gatomek.flightradar.analyzer.adapter.in.notificationProcessorScheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationProcessorUseCase;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final NotificationProcessorUseCase notificationProcessorUseCase;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void scheduleTask() {
        notificationProcessorUseCase.process();
    }
}

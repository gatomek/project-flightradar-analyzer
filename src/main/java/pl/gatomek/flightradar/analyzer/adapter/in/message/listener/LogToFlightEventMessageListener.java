package pl.gatomek.flightradar.analyzer.adapter.in.message.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.adapter.in.message.model.LogToFlightEventMessage;
import pl.gatomek.flightradar.analyzer.application.port.in.LogToFlightEventUseCase;

@RequiredArgsConstructor
@Component
public class LogToFlightEventMessageListener {
    private final LogToFlightEventUseCase logToFlightEventService;

    @Async
    @EventListener
    public void onApplicationEvent(LogToFlightEventMessage message) {
        logToFlightEventService.processLog(message.icao());
    }
}

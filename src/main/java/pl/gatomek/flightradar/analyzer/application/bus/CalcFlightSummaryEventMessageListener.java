package pl.gatomek.flightradar.analyzer.application.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.domain.model.CalcFlightSummaryMessage;
import pl.gatomek.flightradar.analyzer.application.domain.service.FlightSummaryService;

@RequiredArgsConstructor
@Component
public class CalcFlightSummaryEventMessageListener {
    private final FlightSummaryService flightSummaryService;

    @Async
    @EventListener
    public void onApplicationEvent(CalcFlightSummaryMessage message) {
        flightSummaryService.calc(message.uuid());
    }
}

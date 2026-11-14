package pl.gatomek.flightradar.analyzer.adapter.in.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.port.in.LogToFlightEventUseCase;

@RequiredArgsConstructor
@Component
public class NotificationListener {
    private static final String LOG_NOTIFICATION = "LOG_NOTIFICATION";

    private final LogToFlightEventUseCase logToFlightEventUseCase;

    @RabbitListener(queues = LOG_NOTIFICATION)
    public void receiveMessage(Message message) {
        byte[] body = message.getBody();
        String icao = new String(body);
        logToFlightEventUseCase.processLog(icao);
    }
}

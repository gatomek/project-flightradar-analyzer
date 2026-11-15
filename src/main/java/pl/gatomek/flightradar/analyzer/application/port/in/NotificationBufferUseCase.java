package pl.gatomek.flightradar.analyzer.application.port.in;

import java.util.List;

public interface NotificationBufferUseCase {

    void setNotification(String icao);

    List<String> getNotifications();
}

package pl.gatomek.flightradar.analyzer.application.domain.service;

import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationBufferUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class NotificationBufferService implements NotificationBufferUseCase {

    private final Map<String, Boolean> icaoBuffer = new HashMap<>(100);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    public void setNotification(String icao) {
        try {
            writeLock.lock();

            icaoBuffer.put(icao, Boolean.TRUE);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<String> getNotifications() {
        try {
            writeLock.lock();

            List<String> list = new ArrayList<>(icaoBuffer.size());
            icaoBuffer.forEach((key, value) -> {
                if (Boolean.TRUE == value) {
                    list.add(key);
                }
            });

            if (!list.isEmpty()) {
                icaoBuffer.replaceAll((k, v) -> Boolean.FALSE);
            }

            return list;
        } finally {
            writeLock.unlock();
        }
    }
}

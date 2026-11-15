package pl.gatomek.flightradar.analyzer.application.domain.service;

import org.springframework.stereotype.Component;
import pl.gatomek.flightradar.analyzer.application.port.in.NotificationBufferUseCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class NotificationBufferService implements NotificationBufferUseCase {

    private final Set<String> icaoBuffer = new HashSet<>(1000);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    public void setNotification(String icao) {
        try {
            writeLock.lock();

            icaoBuffer.add(icao);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<String> getNotifications() {
        try {
            writeLock.lock();

            List<String> list = new ArrayList<>(icaoBuffer.size());
            list.addAll(icaoBuffer);
            icaoBuffer.clear();
            return list;
        } finally {
            writeLock.unlock();
        }
    }
}

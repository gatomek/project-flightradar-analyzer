package pl.gatomek.flightradar.analyzer.adapter.out.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.adapter.out.mapper.EventMapper;
import pl.gatomek.flightradar.analyzer.adapter.out.model.Event;
import pl.gatomek.flightradar.analyzer.adapter.out.model.EventEntity;
import pl.gatomek.flightradar.analyzer.adapter.out.repo.EventRepository;
import pl.gatomek.flightradar.analyzer.application.port.out.EventServicePort;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServicePortImpl implements EventServicePort {

    private final EventRepository eventRepository;

    @Override
    public List<Event> getAllEventsByIcao(String icao) {
        List<EventEntity> logs = eventRepository.getAllLogsForIcao(icao);
        return EventMapper.INSTANCE.toDomain(logs);
    }
}

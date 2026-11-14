package pl.gatomek.flightradar.analyzer.adapter.out.flightevent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.entity.FlightEventEntity;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.mapper.FlightEventMapper;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.repository.FlightEventRepository;
import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;
import pl.gatomek.flightradar.analyzer.application.port.out.flightevent.FlightEventUseCase;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FlightEventService implements FlightEventUseCase {

    private final FlightEventRepository repository;

    @Override
    public Object[] findFlightByLog(Long logId, String icao) {
        return repository.findFlightByLog(logId, icao);
    }

    @Override
    public FlightEvent findLastFlightByIcao(String icao) {
        FlightEventEntity event = repository.findLastFlightByIcao(icao);
        return FlightEventMapper.INSTANCE.toDomain(event);
    }

    @Override
    public FlightEvent findLastFlightEventByUuid(UUID uuid) {
        FlightEventEntity event = repository.findLastFlightEventByUuid(uuid);
        return FlightEventMapper.INSTANCE.toDomain(event);
    }

    @Override
    public void saveAll(List<FlightEvent> events) {
        List<FlightEventEntity> list = FlightEventMapper.INSTANCE.toEntity(events);
        repository.saveAll(list);
    }
}

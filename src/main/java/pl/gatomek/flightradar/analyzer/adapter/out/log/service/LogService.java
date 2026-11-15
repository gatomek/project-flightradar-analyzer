package pl.gatomek.flightradar.analyzer.adapter.out.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.adapter.out.log.mapper.LogMapper;
import pl.gatomek.flightradar.analyzer.adapter.out.log.model.LogEntity;
import pl.gatomek.flightradar.analyzer.adapter.out.log.repo.LogRepository;
import pl.gatomek.flightradar.analyzer.application.domain.model.Log;
import pl.gatomek.flightradar.analyzer.application.port.out.event.LogUseCase;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LogService implements LogUseCase {

    private final LogRepository repository;

    @Override
    public List<Log> getLogsByIcao(String icao) {
        List<LogEntity> logs = repository.getAllLogsForIcao(icao);
        return LogMapper.INSTANCE.toDomain(logs);
    }

    @Override
    public Optional<Log> findById(Long id) {
        Optional<LogEntity> log = repository.findById(id);
        return log.map(LogMapper.INSTANCE::toDomain);
    }

    @Override
    public List<Log> getAllLogsForIcaoAfterLogId(String icao, Long startLogId)
    {
        List<LogEntity> logs = repository.getAllLogsForIcaoAfterLogId(icao, startLogId);
        return LogMapper.INSTANCE.toDomain(logs);
    }
}

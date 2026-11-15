package pl.gatomek.flightradar.analyzer.application.port.out.event;

import pl.gatomek.flightradar.analyzer.application.domain.model.Log;

import java.util.List;
import java.util.Optional;

public interface LogUseCase {
    List<Log> getLogsByIcao(String icao);
    Optional<Log> findById(Long id);
    List<Log> getAllLogsForIcaoAfterLogId(String icao, Long startLogId);
}

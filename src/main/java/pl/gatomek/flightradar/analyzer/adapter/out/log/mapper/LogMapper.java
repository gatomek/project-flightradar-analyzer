package pl.gatomek.flightradar.analyzer.adapter.out.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.gatomek.flightradar.analyzer.application.domain.model.Log;
import pl.gatomek.flightradar.analyzer.adapter.out.log.model.LogEntity;

import java.util.List;

@Mapper
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    List<Log> toDomain(List<LogEntity> ee);
    Log toDomain(LogEntity ee);
}

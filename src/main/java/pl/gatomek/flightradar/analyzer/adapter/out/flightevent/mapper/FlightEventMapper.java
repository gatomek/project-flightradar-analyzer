package pl.gatomek.flightradar.analyzer.adapter.out.flightevent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.entity.FlightEventEntity;
import pl.gatomek.flightradar.analyzer.application.domain.model.FlightEvent;

import java.util.List;

@Mapper
public interface FlightEventMapper {

    FlightEventMapper INSTANCE = Mappers.getMapper(FlightEventMapper.class);

    List<FlightEvent> toDomain(List<FlightEventEntity> ee);
    FlightEvent toDomain(FlightEventEntity ee);
    FlightEventEntity toEntity(FlightEvent e);
    List<FlightEventEntity> toEntity(List<FlightEvent> e);
}

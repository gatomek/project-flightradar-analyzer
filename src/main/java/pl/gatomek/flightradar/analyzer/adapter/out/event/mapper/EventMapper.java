package pl.gatomek.flightradar.analyzer.adapter.out.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.gatomek.flightradar.analyzer.adapter.out.event.model.Event;
import pl.gatomek.flightradar.analyzer.adapter.out.event.model.EventEntity;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    List<Event> toDomain(List<EventEntity> ee);
}

package pl.gatomek.flightradar.analyzer.adapter.out.flightevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gatomek.flightradar.analyzer.adapter.out.flightevent.entity.FlightEventEntity;

import java.util.UUID;

@Repository
public interface FlightEventRepository extends JpaRepository<FlightEventEntity, Long> {

    // @formatter:off
    @NativeQuery( """
            with last_event_per_flight as (
                select fe.uuid, max(fe.id) as id
                from radar_eventsaver.flightevent fe
                where fe.icao = :icao
                group by fe.uuid
            )
            select l.id as log_id, fe.id as flight_id, fe.startlogid , fe.endlogid , fe.comment
            from last_event_per_flight as lef
            join radar_eventsaver.flightevent fe on lef.id = fe.id
            join radar_eventsaver.event l on fe.icao = l.icao
            where l.id = :logId and l.id >= fe.startlogid and (l.id <= fe.endlogid or fe.endlogid is null);
        """
    )
    // @formatter:on
    Object[] findFlightByLog(@Param("logId") Long logId, @Param("icao") String icao);

    // @formatter:off
    @Query( """
        select fe from FlightEventEntity fe
        where fe.icao = :icao
        order by fe.id desc
        limit 1
        """
    )
    // @formatter:on
    FlightEventEntity findLastFlightByIcao(@Param("icao") String icao);

    // @formatter:off
    @Query( """
        select fe from FlightEventEntity fe
        where fe.uuid = :uuid
        order by fe.id desc
        limit 1
        """
    )
    // @formatter:on
    FlightEventEntity findLastFlightEventByUuid(@Param("uuid") UUID evtUuid);
}

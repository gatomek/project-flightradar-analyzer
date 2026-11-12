package pl.gatomek.flightradar.analyzer.adapter.out.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gatomek.flightradar.analyzer.adapter.out.model.EventEntity;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    // @formatter:off
    @Query( """
        select al from EventEntity al
        where al.icao = :icao
        order by al.id asc
        """
    )
    // @formatter:on
    List<EventEntity> getAllLogsForIcao(@Param("icao") String icao);
}

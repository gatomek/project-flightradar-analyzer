package pl.gatomek.flightradar.analyzer.adapter.out.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gatomek.flightradar.analyzer.adapter.out.log.model.LogEntity;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    // @formatter:off
    @Query( """
        select al from LogEntity al
        where al.id < :logId and al.icao = :icao
        order by al.id desc limit 1
        """
    )
    // @formatter:on
    LogEntity findPrevLog(@Param("logId") Long logId, @Param("icao") String icao);

    // @formatter:off
    @Query( """
        select al from LogEntity al
        where al.icao = :icao
        order by al.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getAllLogsForIcao(@Param("icao") String icao);

    // @formatter:off
    @Query( """
        select al from LogEntity al
        where al.icao = :icao and al.id > :logId
        order by al.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getAllLogsForIcaoAfterLogId(@Param("icao") String icao, @Param("logId") Long logId);

    // @formatter:off
    @Query( """
        select al from LogEntity al
        where al.icao = :icao and al.id >= :beginLogId
        order by al.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getLogsForIcao(@Param("icao") String icao, @Param("beginLogId") Long beginLogId);

    // @formatter:off
    @Query( """
        select al from LogEntity al
        where al.icao = :icao and al.id >= :beginLogId and al.id <= :endLogId
        order by al.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getLogsForIcao(@Param("icao") String icao, @Param("beginLogId") Long beginLogId, @Param("endLogId") Long endLogId);
}

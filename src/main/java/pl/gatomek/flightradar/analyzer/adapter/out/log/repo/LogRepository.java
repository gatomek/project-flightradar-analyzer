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
        select log from LogEntity log
        where log.id < :logId and log.icao = :icao
        order by log.id desc limit 1
        """
    )
    // @formatter:on
    LogEntity findPrevLog(@Param("logId") Long logId, @Param("icao") String icao);

    // @formatter:off
    @Query( """
        select log from LogEntity log
        where log.icao = :icao
        order by log.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getAllLogsForIcao(@Param("icao") String icao);

    // @formatter:off
    @Query( """
        select log from LogEntity log
        where log.icao = :icao and log.id > :logId
        order by log.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getAllLogsForIcaoAfterLogId(@Param("icao") String icao, @Param("logId") Long logId);

    // @formatter:off
    @Query( """
        select log from LogEntity log
        where log.icao = :icao and log.id >= :beginLogId
        order by log.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getLogsForIcao(@Param("icao") String icao, @Param("beginLogId") Long beginLogId);

    // @formatter:off
    @Query( """
        select log from LogEntity log
        where log.icao = :icao and log.id >= :beginLogId and log.id <= :endLogId
        order by log.id asc
        """
    )
    // @formatter:on
    List<LogEntity> getLogsForIcao(@Param("icao") String icao, @Param("beginLogId") Long beginLogId, @Param("endLogId") Long endLogId);
}

package pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.model.FlightSummaryEntity;

@Repository
public interface FlightSummaryRepository extends JpaRepository<FlightSummaryEntity, Long> {
}

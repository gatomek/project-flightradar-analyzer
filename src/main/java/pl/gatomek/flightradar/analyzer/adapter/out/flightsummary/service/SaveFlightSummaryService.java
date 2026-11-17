package pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.model.FlightSummaryEntity;
import pl.gatomek.flightradar.analyzer.adapter.out.flightsummary.repo.FlightSummaryRepository;
import pl.gatomek.flightradar.analyzer.application.port.out.flightsummary.SaveFlightSummaryUseCase;

@RequiredArgsConstructor
@Service
public class SaveFlightSummaryService implements SaveFlightSummaryUseCase {

    private final FlightSummaryRepository repository;

    @Override
    public void save(FlightSummaryEntity summary) {
        repository.save(summary);
    }
}

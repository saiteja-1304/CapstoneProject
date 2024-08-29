package com.CapstoneProject.CarbonFootprintTrack.repository;

import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CarbonTrackRepository extends ReactiveMongoRepository<CarbonFootPrint,Long> {
    Flux<CarbonFootPrint> findByUserId(Long id);
}

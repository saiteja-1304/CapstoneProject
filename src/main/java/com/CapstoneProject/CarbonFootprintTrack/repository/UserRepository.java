package com.CapstoneProject.CarbonFootprintTrack.repository;

import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserDetails,String> {
    UserDetails findById(long userId);
}

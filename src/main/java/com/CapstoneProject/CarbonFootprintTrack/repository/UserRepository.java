package com.CapstoneProject.CarbonFootprintTrack.repository;

import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long> {
    UserDetails findById(long userId);

    boolean existsByUsername(String username);
}

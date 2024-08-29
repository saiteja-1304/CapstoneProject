package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.DashboardResponseDto;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.LeaderBoardDto;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;

    @Autowired
    private UserRepository userRepository;

    public Mono<UserDetails> UserRegister(UserDetails userDetails) {
        userDetails.setTotalCarbonFootprint(0.0);
        return userRepository.save(userDetails);
    }

    //public Mono<UserDetails> loginUser(UserDetails userDetails) {
        //return null;
    //}


    public Flux<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }
}

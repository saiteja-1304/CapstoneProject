package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.UserIdNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.DashboardResponseDto;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.LeaderBoardDto;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.RegisterDto;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.UserDto;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.LeaderboardRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    public String UserRegister(RegisterDto registerDto) {
        if(registerDto.getPassword().equals(registerDto.getConfirm_password())) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(registerDto.getUsername());
            userDetails.setPassword(registerDto.getPassword());
            userDetails.setEmail(registerDto.getEmail());
            userDetails.setLogged_in(false);
            userDetails.setCity(registerDto.getCity());

            userDetails.setTotalCarbonFootprint(0.0);
            userRepository.save(userDetails);
            return "User with " + userDetails.getUsername() + " has been registered successfully";
        }
        return "Password and confirm password does not match";
    }
    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUserProfile(Long userId) {
        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId));

        UserDto userDto = new UserDto();
        userDto.setName(userDetails.getUsername());
        userDto.setEmail(userDetails.getEmail());
        userDto.setCity(userDetails.getCity());
        userDto.setTotalCarbonFootprint(userDetails.getTotalCarbonFootprint());
        userDto.setLogged_in(userDetails.isLogged_in());

        return userDto;
    }
}

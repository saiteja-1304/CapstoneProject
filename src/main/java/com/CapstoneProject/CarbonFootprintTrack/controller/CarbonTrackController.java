package com.CapstoneProject.CarbonFootprintTrack.controller;

import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.entities.CarbonFootprintForm;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import com.CapstoneProject.CarbonFootprintTrack.service.CarbonTrackService;
import com.CapstoneProject.CarbonFootprintTrack.service.LeaderBoardService;
import com.CapstoneProject.CarbonFootprintTrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/carbonTrack")
public class CarbonTrackController {
    @Autowired
    private CarbonTrackService carbonTrackService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaderBoardService  leaderBoardService;

    @PostMapping("/Register")
    public String addUser(RegisterDto registerDto){
        return userService.UserRegister(registerDto);
    }

    @GetMapping("/profile")
    public UserDto  getUserProfile(@RequestParam Long userId){
        return userService.getUserProfile(userId);
    }


    @GetMapping("/allUsers")
    public List<UserDetails> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/allcarbondetails")
    public List<CarbonFootPrint> getAllCarbonDetails(){
        return carbonTrackService.getAllCarbonDetails();
    }

   @GetMapping("/user/{userId}/dashboard")
    public List<DashboardResponseDto> getdashboard(@PathVariable Long userId){
        return carbonTrackService.getDashboard(userId);
   }

   @GetMapping("/user/{userId}/electricity")
    public List<ElectricityDto> getcarbonfootprint(@PathVariable Long userId){
        return carbonTrackService.getElectricityCarbonFootprint(userId);
   }

   @GetMapping("/user/{userId}/wastage")
    public List<WastageDto> getwastage(@PathVariable Long userId){
        return carbonTrackService.getWastageCarbonFootprint(userId);
   }

   @GetMapping("/user/{userId}/transportation")
    public List<TransportationDto> getTransportation(@PathVariable Long userId){
        return carbonTrackService.getTransportation(userId);
   }

   @GetMapping("/leaderBoard/{city}")
    public List<leaderBoard> getLeaderBoard(@PathVariable String city){
        return leaderBoardService.getLeaderBoard(city);
   }


    @PostMapping("/calculateAndSubmit")
    public ResponseEntity<String> calculateAndSubmit(@RequestBody CarbonFootprintForm form) {
        return carbonTrackService.calculateAndSubmit(form);
    }


}

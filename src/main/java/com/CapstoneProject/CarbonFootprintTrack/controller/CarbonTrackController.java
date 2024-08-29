package com.CapstoneProject.CarbonFootprintTrack.controller;

import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.service.CarbonTrackService;
import com.CapstoneProject.CarbonFootprintTrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carbonTrack")
public class CarbonTrackController {
    @Autowired
    private CarbonTrackService carbonTrackService;

    @Autowired
    private UserService userService;

    @PostMapping("/Register")
    public Mono<UserDetails> addUser(UserDetails userDetails){
        return userService.UserRegister(userDetails);
    }

//    @PostMapping("/login")
//    public Mono<UserDetails> loginUser(UserDetails userDetails){
//        return userService.loginUser(userDetails);
//    }

    @GetMapping("/allUsers")
    public Flux<UserDetails> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/carbondetails")
    public Mono<CarbonFootPrint> addCarbonDetails(CarbonFootPrint carbonFootPrint){
        return carbonTrackService.addCarbonDetails(carbonFootPrint);
    }

    @GetMapping("/allcarbondetails")
    public Flux<CarbonFootPrint> getAllCarbonDetails(){
        return carbonTrackService.getAllCarbonDetails();
    }

   @GetMapping("/user/{userId}/dashboard")
    public Flux<DashboardResponseDto> getdashboard(@PathVariable Long userId){
        return carbonTrackService.getdashboard(userId);
   }

   @GetMapping("/user/{userId}/electricity")
    public Flux<ElectricityDto> getcarbonfootprint(@PathVariable Long userId){
        return carbonTrackService.getcarbonfootprint(userId);
   }

   @GetMapping("/user/{userId}/wastage")
    public Flux<WastageDto> getwastage(@PathVariable Long userId){
        return carbonTrackService.getwastage(userId);
   }

   @GetMapping("/user/{userId}/transportation")
    public Flux<TransportationDto> getTransportation(@PathVariable Long userId){
        return carbonTrackService.getTransportation(userId);
   }

   @GetMapping("/leaderBoard")
    public Flux<LeaderBoardDto> getLeaderBoard(){
        return carbonTrackService.getLeaderBoard();
   }



}

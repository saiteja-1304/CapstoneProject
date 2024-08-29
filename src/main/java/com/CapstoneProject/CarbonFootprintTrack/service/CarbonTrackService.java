package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarbonTrackService {
    
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;
    
    @Autowired
    private UserRepository userRepository;

    public void updateUserCarbonTrack(long id, double carbonfootprint){
        UserDetails user = userRepository.findById(id);
        user.setTotalCarbonFootprint(user.getTotalCarbonFootprint() + carbonfootprint);
        userRepository.save(user);
    }
    public Mono<CarbonFootPrint> addCarbonDetails(CarbonFootPrint carbonFootPrint) {
//        return userRepository.findById(String.valueOf(carbonFootPrint.getUserId()))
//                .flatMap(user -> {
//                    user.setTotalCarbonFootprint(user.getTotalCarbonFootprint() + carbonFootPrint.getCarbon_footprint());
//                    return userRepository.save(user)  // Save updated user details
//                            .then(carbonTrackRepository.save(carbonFootPrint));  // Save carbon footprint
//                });
//        UserDetails user = userRepository.findById(carbonFootPrint.getUserId());
//        user.setTotalCarbonFootprint(user.getTotalCarbonFootprint() + carbonFootPrint.getCarbon_footprint());
//        userRepository.save(user);
//        return carbonTrackRepository.save(carbonFootPrint);
        long id = carbonFootPrint.getUserId();
        double carbonfootprint = carbonFootPrint.getCarbon_footprint();
        updateUserCarbonTrack(id, carbonfootprint);
        return carbonTrackRepository.save(carbonFootPrint);
    }

    public Flux<DashboardResponseDto> getdashboard(Long id) {
        Flux<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(id);

        return carbonTrackRepository.findByUserId(id)
                        .sort((cp1, cp2) -> cp2.getToday_date().compareTo(cp1.getToday_date()))  // Sort by date descending
                        .take(30)
                        .map(carbonFootPrint -> {
            DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
            dashboardResponseDto.setDate(carbonFootPrint.getToday_date());
            dashboardResponseDto.setTotalCarbonFootprint(carbonFootPrint.getCarbon_footprint());
            return dashboardResponseDto;
        });
    }


    public Flux<ElectricityDto> getcarbonfootprint(Long userId) {
        return carbonTrackRepository.findByUserId(userId)
                .sort((cp1, cp2) -> cp2.getToday_date().compareTo(cp1.getToday_date()))  // Sort by date descending
                .take(30)  // Limit to the latest 30 records
                .map(carbonFootPrint -> {
                    ElectricityDto electricityDto = new ElectricityDto();
                    electricityDto.setDate(carbonFootPrint.getToday_date());
                    electricityDto.setElectricity(carbonFootPrint.getElectricity());
                    return electricityDto;
                });
    }


    public Flux<WastageDto> getwastage(Long userId) {
        Flux<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        return carbonTrackRepository.findByUserId(userId)
                .sort((cp1, cp2) -> cp2.getToday_date().compareTo(cp1.getToday_date()))  // Sort by date descending
                .take(30)
                .map(carbonFootPrint -> {
            WastageDto wastageDto = new WastageDto();
            wastageDto.setDate(carbonFootPrint.getToday_date());
            wastageDto.setWastage(carbonFootPrint.getWastage());
            return wastageDto;
        });
    }


    public Flux<TransportationDto> getTransportation(Long userId) {
        Flux<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        return carbonTrackRepository.findByUserId(userId)
                .sort((cp1, cp2) -> cp2.getToday_date().compareTo(cp1.getToday_date()))  // Sort by date descending
                .take(30)
                .map(carbonFootPrint -> {
            TransportationDto transportationDto = new TransportationDto();
            transportationDto.setDate(carbonFootPrint.getToday_date());
            transportationDto.setTransportation(carbonFootPrint.getTransportation());
            return transportationDto;
        });
    }

    private LeaderBoardDto transformToLeaderBoardData(CarbonFootPrint carbonFootPrint) {
        LeaderBoardDto dto = new LeaderBoardDto();
        dto.setDate(carbonFootPrint.getToday_date());
        dto.setTotalCarbonFootprint(carbonFootPrint.getCarbon_footprint());
        dto.setUsername(carbonFootPrint.getUsername());
        dto.setCity(carbonFootPrint.getCity());
        return dto;
    }

    // Get the leaderboard sorted by totalCarbonFootprint
    public Flux<LeaderBoardDto> getLeaderBoard() {
        return carbonTrackRepository.findAll()  // Fetch all records
                .map(this::transformToLeaderBoardData)  // Transform to DTO
                .collectSortedList((dto1, dto2) -> Double.compare(dto2.getTotalCarbonFootprint(), dto1.getTotalCarbonFootprint()))  // Sort in descending order
                .flatMapMany(Flux::fromIterable);  // Convert sorted list to Flux
    }


    public Flux<CarbonFootPrint> getAllCarbonDetails() {
        return carbonTrackRepository.findAll();
    }
}

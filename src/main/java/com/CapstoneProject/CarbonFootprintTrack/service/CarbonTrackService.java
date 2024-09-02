package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.UserIdNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.entities.CarbonFootprintForm;
import com.CapstoneProject.CarbonFootprintTrack.entities.Transportation;
import com.CapstoneProject.CarbonFootprintTrack.entities.Wastage;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.UserDetails;
import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.LeaderboardRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
//import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarbonTrackService {
    
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;


    //converting localdate to string
    public String DateConversion(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }





    // get his latest 30 records of total carbon footprint
    public List<DashboardResponseDto> getDashboard(Long id) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(id);

        // Check if the list is empty, meaning no records were found for the given user ID
        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(id);
        }

        // Sort by date in descending order and limit to the latest 30 records
        return footprints.stream()
                .sorted((f1, f2) -> f2.getToday_date().compareTo(f1.getToday_date())) // Sort by date descending
                .limit(30) // Limit to 30 records
                .map(carbonFootPrint -> {
                    DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
                    dashboardResponseDto.setDate(DateConversion(carbonFootPrint.getToday_date()));
                    dashboardResponseDto.setTotalCarbonFootprint(carbonFootPrint.getCarbon_footprint());
                    return dashboardResponseDto;
                })
                .collect(Collectors.toList());
    }


    // get carbon footprint details which are emitted by only electricity
    public List<ElectricityDto> getElectricityCarbonFootprint(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }
        return footprints.stream()
                .map(carbonFootPrint -> {
                    ElectricityDto electricityDto = new ElectricityDto();
                    electricityDto.setDate(DateConversion(carbonFootPrint.getToday_date()));
                    electricityDto.setElectricity(carbonFootPrint.getElectricity());
                    return electricityDto;
                })
                .collect(Collectors.toList());
    }

    // get carbon footprint details which are emitted by only wastage
    public List<WastageDto> getWastageCarbonFootprint(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }
        // Map to WastageDto
        return footprints.stream()
                .map(carbonFootPrint -> {
                    WastageDto wastageDto = new WastageDto();
                    wastageDto.setDate(DateConversion(carbonFootPrint.getToday_date()));
                    wastageDto.setWastage(carbonFootPrint.getWastage());
                    return wastageDto;
                })
                .collect(Collectors.toList());
    }


    // get carbon footprint details which are emitted by only transportation
    public List<TransportationDto> getTransportation(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }
        // Map to WastageDto
        return footprints.stream()
                .map(carbonFootPrint -> {
                    TransportationDto transportationDto = new TransportationDto();
                    transportationDto.setDate(DateConversion(carbonFootPrint.getToday_date()));
                    transportationDto.setTransportation(carbonFootPrint.getTransportation());
                    return transportationDto;
                })
                .collect(Collectors.toList());
    }

    //leaderboard


    public List<CarbonFootPrint> getAllCarbonDetails() {
        return carbonTrackRepository.findAll();
    }

    public ResponseEntity<String> calculateAndSubmit(CarbonFootprintForm form) {
        double transportationEmissions = calculateTransportationEmissions(form.getTransportations());
        double wastageEmissions = calculateWastageEmissions(form.getWastages());
        double electricityEmissions = calculateElectricityEmissions(form.getPrevWatts(), form.getTodayWatts());

        double totalEmissions = transportationEmissions + wastageEmissions + electricityEmissions;

        // Create CarbonFootprint entity and save to database
        CarbonFootPrint carbonFootprint = new CarbonFootPrint();
        carbonFootprint.setUserId(form.getUserId());
        carbonFootprint.setToday_date(LocalDate.now());
        carbonFootprint.setCity(form.getCity());
        carbonFootprint.setUsername(form.getUsername());
        carbonFootprint.setTransportation(transportationEmissions);
        carbonFootprint.setWastage(wastageEmissions);
        carbonFootprint.setElectricity(electricityEmissions);
        carbonFootprint.setCarbon_footprint(totalEmissions);
        carbonFootprint.setTotal_carbon_footprint(userRepository.findById(form.getUserId()).get().getTotalCarbonFootprint()+totalEmissions);
        UserDetails user = userRepository.findById(form.getUserId()).get();
        user.setTotalCarbonFootprint(user.getTotalCarbonFootprint() + totalEmissions);
        userRepository.save(user);
        if(leaderboardRepository.findById(form.getUserId()).isPresent()) {
            leaderBoard leaderboard = leaderboardRepository.findById(form.getUserId()).get();
            leaderboard.setTotal_carbon_footprint(leaderboard.getTotal_carbon_footprint() + totalEmissions);
            leaderboardRepository.save(leaderboard);
        }
        else{
            leaderBoard leaderboard = new leaderBoard();
            leaderboard.setUserId(form.getUserId());
            leaderboard.setTotal_carbon_footprint(totalEmissions);
            leaderboard.setUsername(form.getUsername());
            leaderboard.setCity(form.getCity());
            leaderboardRepository.save(leaderboard);
        }
        carbonTrackRepository.save(carbonFootprint);

        return ResponseEntity.ok("Carbon footprint submitted successfully with total emissions: " + totalEmissions);
    }
    private double calculateTransportationEmissions(List<Transportation> transportations) {
        double emissions = 0.0;
        for (Transportation t : transportations) {
            double emissionFactor = getEmissionFactor(t.getMode());
            if (t.getDistance()!=0.0) {
                emissions += t.getDistance() * emissionFactor;
            } else if (t.getTime() != 0.0) {
                emissions += t.getTime() * (emissionFactor / 10); // Example conversion
            }
        }
        return emissions;
    }

    private double calculateWastageEmissions(List<Wastage> wastages) {
        double emissions = 0.0;
        for (Wastage w : wastages) {
            emissions += (w.getWetWaste() + w.getDryWaste()) * 0.5; // Example emission factor
        }
        return emissions;
    }

    private double calculateElectricityEmissions(int prevWatts, int todayWatts) {
        return (todayWatts - prevWatts) * 0.7; // Example emission factor
    }

    // Helper method to get emission factor based on mode of transportation
    private double getEmissionFactor(String mode) {
        switch (mode.toLowerCase()) {
            case "Car":
                return 0.2;
            case "electric car":
                return 0.1;
            case "Train":
                return 0.05;
            case "Flight":
                return 0.3;
            case "Bus":
                return 0.15;
            default:
                return 0.2;
        }
    }

}

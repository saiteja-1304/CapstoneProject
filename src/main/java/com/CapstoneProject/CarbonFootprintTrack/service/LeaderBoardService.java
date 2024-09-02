package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.CityNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.LeaderboardRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;


    public String DateConversion(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public List<leaderBoard> getLeaderBoard(String city) {

        List<leaderBoard> leaderboard = leaderboardRepository.findAllByCity(city);

        if (leaderboard.isEmpty()) {
            throw new CityNotFoundException(city);
        }

        return leaderboard.stream()
                .sorted((fp1, fp2) -> Double.compare(fp1.getTotal_carbon_footprint(), fp2.getTotal_carbon_footprint())) // Sort in ascending order
                .collect(Collectors.toList());
    }

//    private leaderBoard transformToLeaderBoardData(CarbonFootPrint carbonFootPrint) {
//        leaderBoard dto = new leaderBoard();
//        dto.setToday_date(DateConversion(carbonFootPrint.getToday_date()));
//        dto.setTotal_carbon_footprint(carbonFootPrint.getTotal_carbon_footprint());
//        dto.setUsername(carbonFootPrint.getUsername());
//        dto.setCity(carbonFootPrint.getCity());
//        return dto;
//    }
//
//    // Get the leaderboard sorted by totalCarbonFootprint
//    public List<leaderBoard> getLeaderBoard(String city) {
//        // Fetch all records
//        List<CarbonFootPrint> footprints = carbonTrackRepository.findAllByCity(city);
//
//        // Sort by carbon_footprint in ascending order and transform to LeaderBoardDto
//        return footprints.stream()
//                .sorted((fp1, fp2) -> Double.compare(fp1.getTotal_carbon_footprint(), fp2.getTotal_carbon_footprint())) // Sort in ascending order
//                .map(this::transformToLeaderBoardData)
//                .collect(Collectors.toList()); // Collect into a list
//    }

}

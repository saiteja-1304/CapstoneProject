package com.CapstoneProject.CarbonFootprintTrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="leaderboard")
public class leaderBoard {
    @Id
    private Long userId; // Use Long for consistency and to handle null values
    private String username;
    private String today_date; // Consider using a proper date type for this field
    private String city;
    private double total_carbon_footprint;
}

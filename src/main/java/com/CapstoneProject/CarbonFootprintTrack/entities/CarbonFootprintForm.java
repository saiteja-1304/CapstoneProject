package com.CapstoneProject.CarbonFootprintTrack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarbonFootprintForm {
    private Long userId;
    private LocalDate date;
    private String city;
    private String username;
    private List<Transportation> transportations;
    private List<Wastage> wastages;
    private int prevWatts;
    private int todayWatts;
}

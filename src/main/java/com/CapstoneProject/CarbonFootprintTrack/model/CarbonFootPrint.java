package com.CapstoneProject.CarbonFootprintTrack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="carbonfootprint")
public class CarbonFootPrint {
    @Id
    @Generated
    private long id;
    private long userId;
    private String username;
    private String today_date;
    private String city;
    private double transportation;
    private double electricity;
    private double wastage;
    private double carbon_footprint;
}

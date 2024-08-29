package com.CapstoneProject.CarbonFootprintTrack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection="users")
public class UserDetails {
    @Id
    @Generated
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean logged_in;
    private double totalCarbonFootprint;
    private String city;
}

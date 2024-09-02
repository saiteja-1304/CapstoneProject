package com.CapstoneProject.CarbonFootprintTrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user") // Ensure this matches your database table name
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username is mandatory and it should be unique")
    @Column(unique = true, nullable = false)
    private String username; // Ensure username is unique

    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    private String email;
    private boolean logged_in;
    private double totalCarbonFootprint;
    private String city;
}

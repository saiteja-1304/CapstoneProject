package com.CapstoneProject.CarbonFootprintTrack.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    private String username;
    private String password;
    private String confirm_password;
    private String email;
    private String city;
}

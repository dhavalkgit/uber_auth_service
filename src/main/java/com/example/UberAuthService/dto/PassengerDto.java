package com.example.UberAuthService.dto;

import com.example.UberAuthService.model.Passenger;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public static PassengerDto from(Passenger p){
        return PassengerDto.builder()
                .id(p.getId())
                .email(p.getEmail())
                .name(p.getName())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .build();
    }
}

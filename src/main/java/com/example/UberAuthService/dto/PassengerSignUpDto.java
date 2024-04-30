package com.example.UberAuthService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerSignUpDto {
    private String email;

    private String name;

    private String password;

    private String phoneNumber;
}

package com.example.UberAuthService.service;

import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignUpDto;

public interface AuthService {
    public PassengerDto signUpPassenger(PassengerSignUpDto passengerSignUpDto);
}

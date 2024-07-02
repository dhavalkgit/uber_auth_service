package com.example.UberAuthService.service;

import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignUpDto;
import com.example.uberprojectentityservice.models.Passenger;
import com.example.UberAuthService.repositories.PassengerRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final PassengerRepo passengerRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PassengerRepo passengerRepo, PasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
        this.passengerRepo = passengerRepo;
    }

    @Override
    public PassengerDto signUpPassenger(PassengerSignUpDto passengerSignUpDto) {
        Passenger passenger = Passenger.builder()
                .name(passengerSignUpDto.getName())
                .email(passengerSignUpDto.getEmail())
                .phoneNumber(passengerSignUpDto.getPhoneNumber())
                .password(passwordEncoder.encode(passengerSignUpDto.getPassword()))
                .rating(1.0)
                .build();
        Passenger newPassenger = passengerRepo.save(passenger);
        return PassengerDto.from(newPassenger);
    }

}

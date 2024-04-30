package com.example.UberAuthService.controller;

import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignUpDto;
import com.example.UberAuthService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> passengerSignUp(@RequestBody PassengerSignUpDto passengerSignUpDto){
        PassengerDto response = authService.signUpPassenger(passengerSignUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

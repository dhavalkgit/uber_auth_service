package com.example.UberAuthService.controller;

import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignInDto;
import com.example.UberAuthService.dto.PassengerSignUpDto;
import com.example.UberAuthService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> passengerSignUp(@RequestBody PassengerSignUpDto passengerSignUpDto){
        PassengerDto response = authService.signUpPassenger(passengerSignUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> passengerSignIn(@RequestBody PassengerSignInDto passengerSignInDto){

        System.out.println("request is received" + passengerSignInDto.getEmail() + " "+ passengerSignInDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                passengerSignInDto.getEmail(), passengerSignInDto.getPassword()));

        if(authenticate.isAuthenticated()){
            return new ResponseEntity<>("Authenticated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Authentication is failed", HttpStatus.OK);
    }
}

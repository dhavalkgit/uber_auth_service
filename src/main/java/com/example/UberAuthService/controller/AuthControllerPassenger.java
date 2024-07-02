package com.example.UberAuthService.controller;

import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignInDto;
import com.example.UberAuthService.dto.PassengerSignUpDto;
import com.example.UberAuthService.service.AuthService;
import com.example.UberAuthService.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerPassenger {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthControllerPassenger(AuthService authService, AuthenticationManager authenticationManager,
                                   JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> passengerSignUp(@RequestBody PassengerSignUpDto passengerSignUpDto){
        PassengerDto response = authService.signUpPassenger(passengerSignUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> passengerSignIn(@RequestBody PassengerSignInDto passengerSignInDto,
                                             HttpServletResponse response){

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                passengerSignInDto.getEmail(), passengerSignInDto.getPassword()));

        if(authenticate.isAuthenticated()){
            String jwtToken = jwtService.createToken(passengerSignInDto.getEmail());

            ResponseCookie responseCookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .maxAge(3600)
                    .path("/")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }
        return new ResponseEntity<>("Authentication is failed", HttpStatus.OK);
    }
}

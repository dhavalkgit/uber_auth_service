package com.example.UberAuthService.service;

import com.example.UberAuthService.repositories.PassengerRepo;
import com.example.uberprojectentityservice.models.Passenger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secrate}")
    private String secrete;

    private final PassengerRepo passengerRepo;

    public JwtService(PassengerRepo passengerRepo){
        this.passengerRepo=passengerRepo;
    }

    public SecretKey getKeys(){
        return Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));
    }

    /**
     *  It takes input as JWT token and extract payload for JWT token and return it.
     */
    private Claims extractAllPayload(String token){
        return Jwts.parser()
                .verifyWith(getKeys())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * It used to extract single claim form token based on claim name
     */
    private String extractClaim(String token, String claimName){
        Claims claims = extractAllPayload(token);
        return (String) claims.get(claimName);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllPayload(token);
        return claimResolver.apply(claims);
    }

    /**
     *  check token is expired or not
     */
    private Boolean tokenExpired(String token){
        Date exp = (Date) extractClaim(token, Claims::getExpiration);
        return new Date().before(exp);
    }

    public Boolean isTokenValid(String token, String email){
       return email.equals(extractClaim(token,"email")) && tokenExpired(token);
    }

    /**
     *  The method is generate JWT token
     */
    public String createToken(String username){
        Date now = new Date();
        Date exp = new Date(now.getTime()+ expiry *1000L);

        return  Jwts.builder()
                .claims(getPayload(username))
                .issuedAt(now)
                .expiration(exp)
                .subject(username)
                .signWith(getKeys())
                .compact();
    }

    private Map<String,Object> getPayload(String username){
        Optional<Passenger> passenger = passengerRepo.findByEmail(username);
        Map<String,Object> payload = new HashMap<>();
        if(passenger.isPresent()){
            payload.put("name",passenger.get().getName());
            payload.put("phone",passenger.get().getPhoneNumber());
        }
        return payload;
    }
}

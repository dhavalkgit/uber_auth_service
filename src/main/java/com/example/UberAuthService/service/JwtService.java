package com.example.UberAuthService.service;

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
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secrate}")
    private String secrete;

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
    private String createToken(Map<String,Object> payload, String username){
        Date now = new Date();
        Date exp = new Date(now.getTime()+ expiry *1000L);

        return  Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(exp)
                .subject(username)
                .signWith(getKeys())
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp = new HashMap<>();
        mp.put("email","dhaval@gmail.com");
        mp.put("name","Dhaval");
        mp.put("phoneNumber",8130842);

        String result = createToken(mp,"dhaval10");
        System.out.println(result);
        System.out.println(isTokenValid(result,"dhaval@gmail.com"));
    }
}

package com.example.UberAuthService.dto;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignInDto {
    private String email;
    private String password;
}

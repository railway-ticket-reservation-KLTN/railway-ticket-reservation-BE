package com.example.raiwayticketreservation.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTAuthenticationResponse {
    String token;
}

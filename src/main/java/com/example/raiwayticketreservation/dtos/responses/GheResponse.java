package com.example.raiwayticketreservation.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GheResponse {
    private Long id;
    private String soGhe;
    private String loaiGhe;
}

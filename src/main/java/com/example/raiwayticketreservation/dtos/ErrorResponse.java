package com.example.raiwayticketreservation.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String tenLoi;
    private String moTaLoi;
}

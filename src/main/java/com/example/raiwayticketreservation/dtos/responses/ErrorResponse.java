package com.example.raiwayticketreservation.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String tenLoi;
    private String moTaLoi;
}

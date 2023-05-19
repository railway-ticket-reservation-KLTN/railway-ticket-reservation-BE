package com.example.raiwayticketreservation.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TauResponse {
    private Long id;
    private String tenTau;
}

package com.example.raiwayticketreservation.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimChuyenTauResponse {
    private List hanhTrinhDi;
    private List hanhTrinhVe;
}

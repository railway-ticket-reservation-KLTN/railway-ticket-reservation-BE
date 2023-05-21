package com.example.raiwayticketreservation.dtos.requests;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class GheRequest {
    private Long maToa;
    private String gaDi;
    private String gaDen;
    private Time gioDi;
    private String ngayDi;
    private int soToa;
    private String tenTau;
}

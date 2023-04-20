package com.example.raiwayticketreservation.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class GheRequest {
    private Long maToa;
    private String ngayDi;
    private int soToa;
    private String tenTau;
}

package com.example.raiwayticketreservation.dtos.responses;

import com.example.raiwayticketreservation.entities.VeTau;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Builder
public class MuaVeResponse {
    private List<VeTau> veTaus;
    private String hinhThucThanhToan;
    private Date ngayMuave;
    private Long maDatCho;
}

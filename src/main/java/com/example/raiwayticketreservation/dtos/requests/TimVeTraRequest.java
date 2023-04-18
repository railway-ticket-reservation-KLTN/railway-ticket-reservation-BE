package com.example.raiwayticketreservation.dtos.requests;

import lombok.Data;

@Data
public class TimVeTraRequest {
    public String maDatVe;
    public String tenKhachDat;
    public String email;
    public String soGiayTo;
    public String sdt;
}

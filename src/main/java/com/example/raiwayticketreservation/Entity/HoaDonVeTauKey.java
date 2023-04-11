package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HoaDonVeTauKey implements Serializable {
    @Column(name = "maHoaDon")
    private Long maHoaDon;
    @Column(name = "maVeTau")
    private Long maVeTau;
}

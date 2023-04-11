package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HanhTrinhTauKey implements Serializable {
    @Column(name = "maHanhTrinh")
    private Long mahanhTrinh;
    @Column(name = "maTau")
    private Long maTau;
}

package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HanhTrinhGheKey implements Serializable {
    @Column(name = "maHanhTrinh")
    private Long maHanhTrinh;
    @Column(name = "maGhe")
    private Long maGhe;
}

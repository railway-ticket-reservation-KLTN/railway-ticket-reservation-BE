package com.example.raiwayticketreservation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class VeTauGheKey implements Serializable {
    @Column(name = "maVe")
    private Long maHanhTrinh;

    @Column(name = "maGhe")
    private Long maGhe;
}

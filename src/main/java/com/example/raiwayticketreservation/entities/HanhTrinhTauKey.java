package com.example.raiwayticketreservation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class HanhTrinhTauKey implements Serializable {
    @Column(name = "maHanhTrinh")
    private Long mahanhTrinh;

    @Column(name = "maTau")
    private Long maTau;
}

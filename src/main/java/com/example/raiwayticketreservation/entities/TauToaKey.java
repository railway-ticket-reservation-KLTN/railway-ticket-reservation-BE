package com.example.raiwayticketreservation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TauToaKey implements Serializable {
    @Column(name = "maTau")
    private Long maTau;

    @Column(name = "maToa")
    private Long maToa;
}

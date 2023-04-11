package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;

@Entity
public class TrangThaiGhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maHanhTrinh")
    private HanhTrinh hanhTrinh;

    @ManyToOne
    @JoinColumn(name = "maGhe")
    private Ghe ghe;

    private String trangThai;
}

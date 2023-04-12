package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;

@Entity
public class TrangThaiGhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maVe")
    private VeTau veTau;

    @ManyToOne
    @JoinColumn(name = "maGhe")
    private Ghe ghe;

    private String gaDi;
    private String gaDen;

    private String trangThai;
}

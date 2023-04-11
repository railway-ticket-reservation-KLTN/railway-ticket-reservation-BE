package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "ghe")
public class Ghe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String soGhe;
    private String loaiGhe;
    private int trangThai;

    @OneToMany(mappedBy = "ghe")
    private Set<TrangThaiGhe> trangThaiGhes;

    @ManyToOne
    @JoinColumn(name = "maToa")
    private Toa toa;
}

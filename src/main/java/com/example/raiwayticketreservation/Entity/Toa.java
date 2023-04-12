package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "toa")
public class Toa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenToa;
    private String moTaToa;
    private int soLuongGhe;

    @OneToMany(mappedBy = "toa")
    private Set<CTTauToa> ctTauToas;

    @OneToMany(mappedBy = "toa")
    private Set<Ghe> ghes;
}

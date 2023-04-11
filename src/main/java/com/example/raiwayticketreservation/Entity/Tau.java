package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "tau")
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenTau;
    private int soLuongToa;

    @OneToMany(mappedBy = "tau")
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;

    @OneToMany(mappedBy = "tau")
    private Set<CTTauToa> ctTauToas;
}

package com.example.raiwayticketreservation.entities;

import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(columnDefinition = "VARCHAR(50)")
    private String tenToa;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String moTaToa;

    private int soLuongGhe;

    @OneToMany(mappedBy = "toa")
    @JsonIgnore
    private Set<CTTauToa> ctTauToas;

    @OneToMany(mappedBy = "toa")
    @JsonIgnore
    private Set<Ghe> ghes;
}

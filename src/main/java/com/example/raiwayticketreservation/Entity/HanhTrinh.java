package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "hanhtrinh")
public class HanhTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gaDi;
    private String gaDen;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String ngayDi;
    private String ngayDen;
    private String gioDi;
    private String gioDen;
    private int trangThai;
    private double giaVe;

    @OneToMany(mappedBy = "hanhTrinh")
    private Set<VeTau> veTaus;

    @OneToMany(mappedBy = "hanhTrinh")
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;
}

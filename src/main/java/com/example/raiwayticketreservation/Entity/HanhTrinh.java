package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hanhtrinh")
public class HanhTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String ngayDen;
    private String gioDi;
    private String gioDen;
    private int trangThai;
    private double giaVe;

    @OneToMany(mappedBy = "hanhTrinh")
    @JsonIgnore
    private Set<VeTau> veTaus;

    @OneToMany(mappedBy = "hanhTrinh")
    @JsonIgnore
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;
}

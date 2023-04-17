package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vetau")
public class VeTau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenHanhKhach;
    private String soGiayTo;
    private double donGia;
    private String loaiVe;
    private String doiTuong;
    private String tinhTrang;
    private int trangThai;

    @OneToMany(mappedBy = "veTau")
    Set<CTHD> cthd;

    @ManyToOne
    @JoinColumn(name = "maKhachDat")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private KhachDatVe khachDatVe;


    @ManyToOne
    @JoinColumn(name = "maHanhTrinh")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HanhTrinh hanhTrinh;

//    @OneToMany(mappedBy = "veTau")
//    private Set<TrangThaiGhe> trangThaiGhes;
}

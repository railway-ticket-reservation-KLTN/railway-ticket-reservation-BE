package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "hoadon")
@ToString
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "maKhachDat")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private KhachDatVe khachDatVe;
    private Date ngayLap;
    private String hinhThucThanhToan;
    private int trangThai;

    @OneToMany(mappedBy = "hoaDon")
    private Set<CTHD> cthd;
}

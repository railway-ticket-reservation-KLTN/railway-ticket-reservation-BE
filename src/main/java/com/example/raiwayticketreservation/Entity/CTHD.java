package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "CTHD")
public class CTHD {
    @EmbeddedId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HoaDonVeTauKey id;

    @ManyToOne
    @MapsId("maHoaDon")
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maVeTau")
    @JoinColumn(name = "maVeTau")
    private VeTau veTau;

    private int soLuong;
    private double donGia;
}

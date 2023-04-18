package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vetau")
public class VeTau implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maVe;
    private String tenHanhKhach;
    private String soGiayTo;
    private double donGia;
    private String loaiVe;
    private String doiTuong;
    private String tenTau;
    private int soGhe;
    private int soToa;
    private String tinhTrang;
    private int trangThai;

    @OneToMany(mappedBy = "veTau")
    @JsonIgnore
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

}

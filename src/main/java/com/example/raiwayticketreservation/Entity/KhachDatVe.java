package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "khachdatve")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KhachDatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hoTen;
    private String soGiayTo;
    private String email;
    private String sdt;


    @OneToMany(mappedBy = "khachDatVe", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDon> hoaDon;

    @OneToMany(mappedBy = "khachDatVe", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VeTau> veTaus;
}

package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Table(name = "trangthaighe")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrangThaiGhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maVe")
    private VeTau veTau;

    @ManyToOne
    @JoinColumn(name = "maGhe")
    private Ghe ghe;

    private String gaDi;
    private String gaDen;

    private String trangThai;
}

package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "banggiave")
public class BangGiaVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenTo;

    @OneToOne
    @JoinColumn(name = "loaiToa")
    private Toa toa;
}

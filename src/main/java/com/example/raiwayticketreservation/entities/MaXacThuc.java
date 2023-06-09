package com.example.raiwayticketreservation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maxacthuc")
public class MaXacThuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)")
    private String maXacThuc;

    private Timestamp timestamp;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ma_khach_dat", referencedColumnName = "id")
    private KhachDatVe khachDatVe;
}

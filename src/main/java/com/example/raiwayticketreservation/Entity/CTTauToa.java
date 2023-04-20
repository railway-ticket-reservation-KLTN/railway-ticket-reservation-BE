package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "cttautoa")
public class CTTauToa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("maTau")
    @JoinColumn(name = "maTau")
    private Tau tau;

    @ManyToOne
    @MapsId("maToa")
    @JoinColumn(name = "maToa")
    private Toa toa;

    @Column(columnDefinition = "VARCHAR(10)")
    private String soToa;

    private Long maHanhTrinh;
}

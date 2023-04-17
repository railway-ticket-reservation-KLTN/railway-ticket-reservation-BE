package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CTHD")
public class CTHD {
    @EmbeddedId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private HoaDonVeTauKey id;

    @ManyToOne
    @MapsId("maHoaDon")
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maVeTau")
    @JoinColumn(name = "maVeTau")
    private VeTau veTau;
    private double donGia;
}

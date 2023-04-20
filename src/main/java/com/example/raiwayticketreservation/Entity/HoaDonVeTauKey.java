package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
public class HoaDonVeTauKey implements Serializable {
    @Column(name = "maHoaDon")
    private Long maHoaDon;

    @Column(name = "maVeTau")
    private Long maVeTau;
}

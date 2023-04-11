package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "cthanhtrinhtau")
public class CTHanhTrinhTau {
    @EmbeddedId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HanhTrinhTauKey id;

    @ManyToOne
    @MapsId("maHanhTrinh")
    @JoinColumn(name = "maHanhTrinh")
    private HanhTrinh hanhTrinh;

    @ManyToOne
    @MapsId("maTau")
    @JoinColumn(name = "maTau")
    private Tau tau;

    private int khoangCach;
}

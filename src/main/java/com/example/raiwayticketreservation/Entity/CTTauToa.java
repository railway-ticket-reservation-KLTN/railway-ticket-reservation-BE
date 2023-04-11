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

    @EmbeddedId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TauToaKey tauToaKey;

    @ManyToOne
    @MapsId("maTau")
    @JoinColumn(name = "maTau")
    private Tau tau;

    @ManyToOne
    @MapsId("maToa")
    @JoinColumn(name = "maToa")
    private Toa toa;
}

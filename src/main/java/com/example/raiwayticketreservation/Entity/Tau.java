package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "tau")
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)")
    private String tenTau;

    private int soLuongToa;

    @OneToMany(mappedBy = "tau", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<HanhTrinh> hanhTrinhs;

    @OneToMany(mappedBy = "tau")
    private Set<CTTauToa> toas;
}

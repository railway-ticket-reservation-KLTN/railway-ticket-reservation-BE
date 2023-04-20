package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "khachdatve")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KhachDatVe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(150)")
    private String hoTen;

    @Column(columnDefinition = "VARCHAR(20)")
    private String soGiayTo;

    @Column(columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(columnDefinition = "VARCHAR(15)")
    private String sdt;

    @OneToMany(mappedBy = "khachDatVe", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<HoaDon> hoaDon;

    @OneToMany(mappedBy = "khachDatVe", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<VeTau> veTaus;
}

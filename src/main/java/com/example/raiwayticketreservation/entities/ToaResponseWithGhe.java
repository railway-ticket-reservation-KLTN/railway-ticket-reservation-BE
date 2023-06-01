package com.example.raiwayticketreservation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToaResponseWithGhe {
    public Long maToa;
    public String soToa;
    public String tenTau;
    public String moTaToa;
    public int soLuongGhe;
    public String tenToa;
    public Set ghes;
}

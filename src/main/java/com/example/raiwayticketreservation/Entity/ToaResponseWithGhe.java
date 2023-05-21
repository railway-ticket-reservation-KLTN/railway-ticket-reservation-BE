package com.example.raiwayticketreservation.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ToaResponseWithGhe {
    public Long maToa;
    public String soToa;
    public String tenTau;
    public String moTaToa;
    public int soLuongGhe;
    public String tenToa;
    public Set ghes;
}

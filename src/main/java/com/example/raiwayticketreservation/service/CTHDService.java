package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.CTHD;

import java.util.List;
import java.util.Set;

public interface CTHDService {
    public List<CTHD> themCTHD(Set<CTHD> cthds);

    public Set<CTHD> getCTHDTheoHoaDonId(Long id);
}

package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.CTHD;

import java.util.List;
import java.util.Set;

public interface CTHDService {
    public List<CTHD> themCTHD(Set<CTHD> cthds);

    public Set<CTHD> getCTHDTheoHoaDonId(Long id);
}

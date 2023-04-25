package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VeTauService {
    public List<VeTau> themVe(Set<VeTau> veTau);
    public Long getIDVeTau(VeTau veTau);

    public VeTau getVeTheoMaVe(KiemTraVeRequest kiemTraVeRequest);

    public VeTau getVeTauTheoID(Long Id);

    public void capNhatTrangThaiTinhTrangVeTau(VeTau veTau);

    public Set<VeTau> getVeTauTheoMaKhachDat(Long maKhachDat);

}

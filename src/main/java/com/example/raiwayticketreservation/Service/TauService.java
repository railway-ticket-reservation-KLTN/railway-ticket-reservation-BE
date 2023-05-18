package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.Tau;

import java.util.List;

public interface TauService {
    public Long getIdTauTheoTenTau(String tenTau);
    public List<Tau> getDanhSachTau();
}

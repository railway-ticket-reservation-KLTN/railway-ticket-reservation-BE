package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.Tau;

import java.util.List;

public interface TauService {
    public Long getIdTauTheoTenTau(String tenTau);
    public List<Tau> getDanhSachTau();
}

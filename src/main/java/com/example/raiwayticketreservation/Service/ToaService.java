package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.responses.ToaResponse;

import java.util.Set;

public interface ToaService {

    public Set<ToaResponse> getToasByHanhTrinhIDTauID(Long hanhTrinhID, Long tauID);
}

package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;

import java.util.ArrayList;
import java.util.Set;

public interface TauService {
    public Set<Tau> getTauByHanhTrinhID(Long id);

    public ArrayList<ToaResponse> getToaTheoTauByHanhTrinhIDTauID(Long hanhTrinhID, String tenTau);
}

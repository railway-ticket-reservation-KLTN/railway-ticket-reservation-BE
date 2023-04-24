package com.example.raiwayticketreservation.Service.ServiceImpl;


import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.Repository.TauRepo;
import com.example.raiwayticketreservation.Service.TauService;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class TauServiceImpl implements TauService {
    @Autowired
    private TauRepo tauRepo;

    @Override
    public Set<Tau> getTauByHanhTrinhID(Long id) {
        return tauRepo.getTau(id);
    }

    @Override
    public ArrayList<ToaResponse> getToaTheoTauByHanhTrinhIDTauID(Long hanhTrinhID) {
        return tauRepo.getToaTheoTauByMaHanhTrinhMaTau(hanhTrinhID);
    }
}
